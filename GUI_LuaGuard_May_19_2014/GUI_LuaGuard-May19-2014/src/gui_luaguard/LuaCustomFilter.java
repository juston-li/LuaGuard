/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui_luaguard;

import java.io.File;

/**
 *
 * @author willmbp15
 */
class LuaCustomFilter extends javax.swing.filechooser.FileFilter {

  @Override
  public boolean accept(File file) {
    // Allow only directories, or files with ".lua" extension
    return file.isDirectory() || file.getAbsolutePath().endsWith(".lua") || file.getAbsolutePath().endsWith(".Lua");
  }

  @Override
  public String getDescription() {
    // This description will be displayed in the dialog,
    // hard-coded = ugly, should be done via I18N
    return "Lua documents (*.lua)";
  }

}
