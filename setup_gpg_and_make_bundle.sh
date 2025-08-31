#!/usr/bin/env bash
set -euo pipefail

# ================================
# GPG + Central bundle helper (macOS)
# ================================
# What it does:
# 1) Ensure gnupg + pinentry-mac + loopback configured
# 2) Create a GPG key (RSA 4096) with passphrase in batch mode
# 3) Send public key to keyserver.ubuntu.com
# 4) Export secret key (ASCII) to ~/SIGNING_KEY.asc
# 5) (optional) Sign artifacts in ARTIFACT_DIR and zip a central bundle
#
# Usage (interactive):
#   ./setup_gpg_and_make_bundle.sh
#
# Usage (non-interactive via env):
#   REALNAME="Your Name" EMAIL="you@example.com" EXPIRE="2y" \
#   PASSPHRASE="your-strong-pass" \
#   ARTIFACT_DIR="/path/to/.m2/.../yourversion" \
#   OUT_ZIP="central-bundle-yourlib-1.0.1.zip" \
#   ./setup_gpg_and_make_bundle.sh
#
# Notes:
# - If ARTIFACT_DIR is provided and contains *.aar/*.jar/*.pom, we will sign & zip.

# ---------- 0) Inputs ----------
REALNAME="${REALNAME:-}"
EMAIL="${EMAIL:-}"
EXPIRE="${EXPIRE:-2y}"           # e.g. 2y / 1y / 0 (no expiry)
PASSPHRASE="${PASSPHRASE:-}"
ARTIFACT_DIR="${ARTIFACT_DIR:-}" # e.g. /Users/you/.m2/repository/com/foo/bar/yourlib/1.0.1
OUT_ZIP="${OUT_ZIP:-central-bundle.zip}"

# Prompt if not provided
if [[ -z "${REALNAME}" ]]; then
  read -rp "Real name for GPG key: " REALNAME
fi
if [[ -z "${EMAIL}" ]]; then
  read -rp "Email for GPG key: " EMAIL
fi
if [[ -z "${PASSPHRASE}" ]]; then
  read -rsp "Passphrase for GPG key (input hidden): " PASSPHRASE; echo
fi

echo "==> Using:"
echo "    Name       : ${REALNAME}"
echo "    Email      : ${EMAIL}"
echo "    Expires    : ${EXPIRE}"
[[ -n "${ARTIFACT_DIR}" ]] && echo "    ArtifactDir : ${ARTIFACT_DIR}"
echo

# ---------- 1) Ensure dependencies ----------
if ! command -v gpg >/dev/null 2>&1; then
  echo "Installing gnupg via Homebrew..."
  brew install gnupg
fi
if ! command -v pinentry-mac >/dev/null 2>&1; then
  echo "Installing pinentry-mac via Homebrew..."
  brew install pinentry-mac
fi

mkdir -p "${HOME}/.gnupg"
# Use mac pinentry when needed AND allow loopback pinentry for batch
grep -q "pinentry-program" "${HOME}/.gnupg/gpg-agent.conf" 2>/dev/null || \
  echo "pinentry-program $(brew --prefix)/bin/pinentry-mac" >> "${HOME}/.gnupg/gpg-agent.conf"
grep -q "^allow-loopback-pinentry" "${HOME}/.gnupg/gpg-agent.conf" 2>/dev/null || \
  echo "allow-loopback-pinentry" >> "${HOME}/.gnupg/gpg-agent.conf"

chmod 700 "${HOME}/.gnupg" || true
killall gpg-agent 2>/dev/null || true

# ---------- 2) Generate GPG key in batch (RSA 4096 + passphrase) ----------
TMPDIR="$(mktemp -d)"
KEYCFG="${TMPDIR}/gpg-key-batch.txt"
cat > "${KEYCFG}" <<EOF
Key-Type: RSA
Key-Length: 4096
Name-Real: ${REALNAME}
Name-Email: ${EMAIL}
Expire-Date: ${EXPIRE}
Passphrase: ${PASSPHRASE}
%commit
EOF

echo "==> Generating GPG key..."
gpg --batch --pinentry-mode loopback --generate-key "${KEYCFG}"

# ---------- 3) Extract KEYID ----------
# Use machine-readable output:
KEYID="$(gpg --list-secret-keys --with-colons | awk -F: '$1=="sec"{print $5; exit}')"
if [[ -z "${KEYID}" ]]; then
  echo "ERROR: Could not determine KEYID"; exit 1
fi
echo "==> KEYID: ${KEYID}"

# ---------- 4) Send public key to keyserver ----------
echo "==> Sending public key to keyserver.ubuntu.com ..."
gpg --keyserver keyserver.ubuntu.com --send-keys "${KEYID}" || {
  echo "WARN: Could not send key to keyserver (you can retry later)."
}

# ---------- 5) Export secret key (ASCII) ----------
echo "==> Exporting secret key to ~/SIGNING_KEY.asc"
gpg --armor --export-secret-keys "${KEYID}" > "${HOME}/SIGNING_KEY.asc"

echo
echo "==> Set these env vars for Gradle signing (append to your ~/.zshrc):"
echo "export SIGNING_KEY=\"\$(cat ${HOME}/SIGNING_KEY.asc)\""
echo "export SIGNING_PASSWORD=\"${PASSPHRASE}\""
echo

# ---------- 6) Optional: sign artifacts & make central bundle ----------
if [[ -n "${ARTIFACT_DIR}" ]]; then
  if [[ -d "${ARTIFACT_DIR}" ]]; then
    echo "==> Signing artifacts in: ${ARTIFACT_DIR}"
    pushd "${ARTIFACT_DIR}" >/dev/null

    shopt -s nullglob
    FILES=( *.aar *.jar *.pom )
    if [[ ${#FILES[@]} -eq 0 ]]; then
      echo "WARN: No *.aar/*.jar/*.pom found in ${ARTIFACT_DIR}"
    else
      for f in "${FILES[@]}"; do
        if [[ ! -f "${f}.asc" ]]; then
          echo "   - Signing ${f}"
          gpg --armor --batch --yes --pinentry-mode loopback --passphrase "${PASSPHRASE}" --detach-sign "${f}"
        else
          echo "   - Already signed: ${f}"
        fi
      done
      echo "==> Creating bundle: ${OUT_ZIP}"
      zip -r "${OUT_ZIP}" * -x "*.sha1" "*.md5" >/dev/null
      echo "==> Bundle ready: ${ARTIFACT_DIR}/${OUT_ZIP}"
    fi
    popd >/dev/null
  else
    echo "WARN: ARTIFACT_DIR not found: ${ARTIFACT_DIR}"
  fi
fi

echo
echo "✅ All done."
echo "- KEYID: ${KEYID}"
echo "- Secret key: ${HOME}/SIGNING_KEY.asc"
[[ -n "${ARTIFACT_DIR}" ]] && echo "- If provided, bundle: ${ARTIFACT_DIR}/${OUT_ZIP}"

