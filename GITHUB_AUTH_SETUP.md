# 🔐 GitHub Authentication Setup Guide

## 🚨 Authentication Required

GitHub requires authentication to push code. You have two main options:

## Option 1: Personal Access Token (PAT) - Recommended

### Step 1: Create Personal Access Token
1. **Go to GitHub**: https://github.com
2. **Click your profile picture** → Settings
3. **Scroll down** → Developer settings
4. **Personal access tokens** → Tokens (classic)
5. **Generate new token** → Generate new token (classic)
6. **Token settings**:
   - **Note**: "Ecommerce App Push Token"
   - **Expiration**: 90 days (or your preference)
   - **Scopes**: Check "repo" (full control of private repositories)
7. **Generate token**
8. **COPY THE TOKEN** (you won't see it again!)

### Step 2: Push with Token
```bash
cd /mnt/d/Users/imdinkar/projects/java/ecommerce-app-to-share

# When prompted for password, use your Personal Access Token
git push -u origin main

# Username: imdinkar
# Password: [paste your Personal Access Token here]
```

### Step 3: Store Credentials (Optional)
```bash
# Store credentials to avoid repeated entry
git config --global credential.helper store
```

## Option 2: SSH Key Setup

### Step 1: Generate SSH Key
```bash
# Generate new SSH key
ssh-keygen -t ed25519 -C "your_email@example.com"

# Press Enter to accept default file location
# Enter passphrase (optional but recommended)
```

### Step 2: Add SSH Key to GitHub
```bash
# Copy public key to clipboard
cat ~/.ssh/id_ed25519.pub
```

1. **Go to GitHub**: Settings → SSH and GPG keys
2. **New SSH key**
3. **Paste the public key**
4. **Add SSH key**

### Step 3: Change Remote to SSH
```bash
cd /mnt/d/Users/imdinkar/projects/java/ecommerce-app-to-share

# Remove HTTPS remote
git remote remove origin

# Add SSH remote
git remote add origin git@github.com:imdinkar/ecommerce-app-v1.git

# Push with SSH
git push -u origin main
```

## 🚀 Quick Push Commands

### Using Personal Access Token:
```bash
cd /mnt/d/Users/imdinkar/projects/java/ecommerce-app-to-share
git push -u origin main
# Username: imdinkar
# Password: [Your Personal Access Token]
```

### Using SSH (after setup):
```bash
cd /mnt/d/Users/imdinkar/projects/java/ecommerce-app-to-share
git remote set-url origin git@github.com:imdinkar/ecommerce-app-v1.git
git push -u origin main
```

## ✅ After Successful Push

Your repository will contain:
- ✅ 59 files
- ✅ 8,281+ lines of code
- ✅ Complete Spring Boot application
- ✅ Professional documentation
- ✅ Setup scripts and guides

## 🔍 Verify Upload

1. **Visit**: https://github.com/imdinkar/ecommerce-app-v1
2. **Check**: All files are uploaded
3. **Verify**: README.md displays correctly
4. **Confirm**: Documentation is accessible

## 📞 Troubleshooting

**Token doesn't work:**
- Make sure you selected "repo" scope
- Copy token exactly (no extra spaces)
- Token might have expired

**SSH key issues:**
- Make sure public key is copied correctly
- Check SSH agent is running: `ssh-add -l`
- Test connection: `ssh -T git@github.com`

---

**Current Status**: Ready to push with authentication  
**Repository**: https://github.com/imdinkar/ecommerce-app-v1.git  
**Files Ready**: 59 files committed and ready
