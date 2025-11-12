# BTLOOP - Arkanoid Game Setup Guide

## 🚀 Setup Instructions for Collaborators

### Prerequisites
- IntelliJ IDEA
- JavaFX SDK 25
- JDK (Valhalla EA or compatible)

### Initial Setup Steps

1. **Clone the repository**
   ```bash
   git clone <repository-url>
   cd BTLOOP
   ```

2. **Configure Resources Folder (IMPORTANT!)**
   - In IntelliJ, right-click on the `resources` folder
   - Select **"Mark Directory as"** → **"Resources Root"**
   - This ensures images and CSS files are copied to the output directory

3. **Configure JavaFX**
   - Go to **File → Project Structure → Libraries**
   - Add JavaFX SDK library (if not already added)
   - Path: `C:\JavaFX\javafx-sdk-25.0.1\lib`

4. **Rebuild Project**
   - Select **Build → Rebuild Project**
   - This will copy all resources to `out/production/BTLOOP/`

5. **Run the Game**
   - Right-click `src/main/Main.java`
   - Select **"Run 'Main.main()'"**

---

## ⚠️ Common Issues

### Issue: Images not showing / CSS not loading
**Solution:** Make sure you marked `resources` as Resources Root and rebuilt the project.

### Issue: FXML files not found
**Solution:** The FXML paths have been updated. Make sure you pulled the latest changes from the `TuanAnhFix` branch.

---

## 📁 Project Structure

```
BTLOOP/
├── src/
│   ├── main/           # Main entry point
│   ├── controller/     # Controllers and FXML files
│   ├── gameobjects/    # Game objects (Ball, Brick, Paddle, etc.)
│   ├── mng/            # Game managers
│   └── user/           # User management
├── resources/          # *** MUST be marked as Resources Root
│   ├── gfx/           # Images
│   ├── sfx/           # Sound effects
│   ├── ttf/           # Fonts
│   ├── style.css      # Styles for gameplay
│   └── style2.css     # Styles for login/menu
└── out/               # Build output (auto-generated)
```

---

## 🔧 Recent Changes (Important!)

### Resource Path Updates
All FXML files now use classpath-based paths:
- Old: `@../../../resources/gfx/...`
- New: `@/gfx/...`

This requires the `resources` folder to be marked as Resources Root in IntelliJ.

### FXML Loader Paths
Updated in `gameInfo.java`:
- Old: `/login.fxml`
- New: `/controller/fxml/login.fxml`

---

## 👥 For Collaborators

After pulling the latest changes:
1. ✅ Mark `resources` as Resources Root
2. ✅ Rebuild the project
3. ✅ Run and test

If you encounter any issues, contact the team lead.
