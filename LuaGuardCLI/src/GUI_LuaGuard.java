/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.awt.Component;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.ButtonGroup;
import javax.swing.JFileChooser;
import javax.swing.JRadioButton;

public class GUI_LuaGuard extends javax.swing.JFrame {

  File fileSelectedDirectory;

  private Component frame;
  public static ArrayList<String> arrayList_String_Blacklist = new ArrayList<String>();

  public static ArrayList<String> arrayList_Lua_Input_Files = new ArrayList<String>();

  //////////////////////////////////////////////////////////////////////////////
  /**
   * Creates new form LuaguardGui
   */
  public GUI_LuaGuard() {
    initComponents();
  }

  //////////////////////////////////////////////////////////////////////////////
  /**
   * ThinputStreamReference method inputStreamReference called from within the
   * constructor to initialize the form. WARNING: Do NOT modify
   * thinputStreamReference code. The content of thinputStreamReference method
   * inputStreamReference always regenerated by the Form Editor.
   */
  @SuppressWarnings("unchecked")
  // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
  private void initComponents() {

    jFileChooserGetLuaFile = new javax.swing.JFileChooser();
    jButton4 = new javax.swing.JButton();
    jFileChooserGetBlackListFile = new javax.swing.JFileChooser();
    jLabel8 = new javax.swing.JLabel();
    jFileChooserGetLuaFolder = new javax.swing.JFileChooser();
    jPanelLower = new javax.swing.JPanel();
    jLabelLuaIcon = new javax.swing.JLabel();
    jLabelObfuscationOptions = new javax.swing.JLabel();
    jLabelGetFileToObfuscate = new javax.swing.JLabel();
    jButtonGetLuaFile = new javax.swing.JButton();
    jLabelBottomIconImage = new javax.swing.JLabel();
    jLabelDeveloperCredits = new javax.swing.JLabel();
    jButtonExitProgram = new javax.swing.JButton();
    jTextFieldMakeOutputFile = new javax.swing.JTextField();
    jLabelGetBlackListFile = new javax.swing.JLabel();
    jButtonGetBlackListFile = new javax.swing.JButton();
    jButtonObfuscate = new javax.swing.JButton();
    jButtonGetLuaFolder = new javax.swing.JButton();
    jLabelImportLuaFolder = new javax.swing.JLabel();
    jButtonHelp = new javax.swing.JButton();
    jLabelPart2ofGetLuaFolderLabel = new javax.swing.JLabel();
    jRadioButtonLevel3Obfuscate = new javax.swing.JRadioButton();
    jRadioButtonLevel0Obfuscate = new javax.swing.JRadioButton();
    jRadioButtonLevel1Obfuscate = new javax.swing.JRadioButton();
    jRadioButtonLevel2Obfuscate = new javax.swing.JRadioButton();
    jButtonDestinationDirectory = new javax.swing.JButton();
    jButtonGetAST = new javax.swing.JButton();
    jLabelLuaGuardObfuscatorv10 = new javax.swing.JLabel();

    jFileChooserGetLuaFile.setFileFilter(new LuaCustomFilter());
    jFileChooserGetLuaFile.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        jFileChooserGetLuaFileActionPerformed(evt);
      }
    });

    jButton4.setText("jButton4");

    jFileChooserGetBlackListFile.setFileFilter(new TextCustomFilter());

    jLabel8.setText("Types of Obfuscation");

    jFileChooserGetLuaFolder.setFileSelectionMode(javax.swing.JFileChooser.DIRECTORIES_ONLY);
    jFileChooserGetLuaFolder.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        jFileChooserGetLuaFolderActionPerformed(evt);
      }
    });

    setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

    jPanelLower.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 51, 153), 4));

    jLabelLuaIcon.setIcon(new javax.swing.ImageIcon(getClass().getResource("lua.gif"))); // NOI18N

    jLabelObfuscationOptions.setFont(new java.awt.Font("Lucida Grande", 1, 14)); // NOI18N
    jLabelObfuscationOptions.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
    jLabelObfuscationOptions.setText(" Pre-Obfuscation Tasks");

    jLabelGetFileToObfuscate.setText("Get File to Obfuscate");

    jButtonGetLuaFile.setText("Get Lua File");
    jButtonGetLuaFile.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        jButtonGetLuaFileActionPerformed(evt);
      }
    });

    jLabelBottomIconImage.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
    jLabelBottomIconImage.setIcon(new javax.swing.ImageIcon(getClass().getResource("DSD-pku-uo.jpg"))); // NOI18N

    jLabelDeveloperCredits.setText("                                                LuaGuard is a joint project between PKU and UO Software developers");

    jButtonExitProgram.setText("Exit Program");
    jButtonExitProgram.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        jButtonExitProgramActionPerformed(evt);
      }
    });

    jTextFieldMakeOutputFile.setHorizontalAlignment(javax.swing.JTextField.LEFT);
    jTextFieldMakeOutputFile.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
    jTextFieldMakeOutputFile.setEnabled(false);
    jTextFieldMakeOutputFile.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        jTextFieldMakeOutputFileActionPerformed(evt);
      }
    });

    jLabelGetBlackListFile.setText("   Get Black List File");

    jButtonGetBlackListFile.setText("Get Black List File");
    jButtonGetBlackListFile.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        jButtonGetBlackListFileActionPerformed(evt);
      }
    });

    jButtonObfuscate.setText("Obfuscate");
    jButtonObfuscate.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        jButtonObfuscateActionPerformed(evt);
      }
    });

    jButtonGetLuaFolder.setText("Get Lua Folder");
    jButtonGetLuaFolder.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        jButtonGetLuaFolderActionPerformed(evt);
      }
    });

    jLabelImportLuaFolder.setText("Get Entire Lua Folder");

    jButtonHelp.setText("Help");

    jLabelPart2ofGetLuaFolderLabel.setText("      To Obfuscate");

    jRadioButtonLevel3Obfuscate.setText("Level 3 Obfuscation");
    jRadioButtonLevel3Obfuscate.addActionListener(new java.awt.event.ActionListener() {
    	public void actionPerformed(java.awt.event.ActionEvent evt) {
    		jRadioButtonLevel3ObfuscateActionPerformed(evt);
    	}
    });
    jRadioButtonLevel0Obfuscate.setText("Level 0 Obfuscation");
    jRadioButtonLevel0Obfuscate.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        jRadioButtonLevel0ObfuscateActionPerformed(evt);
      }
    });

    jRadioButtonLevel1Obfuscate.setText("Level 1 Obfuscation");
    jRadioButtonLevel1Obfuscate.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        jRadioButtonLevel1ObfuscateActionPerformed(evt);
      }
    });

    jRadioButtonLevel2Obfuscate.setText("Level 2 Obfuscation");
    jRadioButtonLevel2Obfuscate.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        jRadioButtonLevel2ObfuscateActionPerformed(evt);
      }
    });

    jButtonDestinationDirectory.setText("Destination Directory");
    jButtonDestinationDirectory.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        jButtonDestinationDirectoryActionPerformed(evt);
      }
    });

    jButtonGetAST.setText("Get AST");
    jButtonGetAST.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        jButtonGetASTActionPerformed(evt);
      }
    });

    javax.swing.GroupLayout jPanelLowerLayout = new javax.swing.GroupLayout(jPanelLower);
    jPanelLower.setLayout(jPanelLowerLayout);
    jPanelLowerLayout.setHorizontalGroup(
      jPanelLowerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGroup(jPanelLowerLayout.createSequentialGroup()
        .addGap(20, 20, 20)
        .addComponent(jLabelLuaIcon)
        .addGroup(jPanelLowerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
          .addGroup(jPanelLowerLayout.createSequentialGroup()
            .addGroup(jPanelLowerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
              .addGroup(jPanelLowerLayout.createSequentialGroup()
                .addGap(101, 101, 101)
                .addComponent(jLabelObfuscationOptions, javax.swing.GroupLayout.PREFERRED_SIZE, 253, javax.swing.GroupLayout.PREFERRED_SIZE))
              .addGroup(jPanelLowerLayout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addComponent(jRadioButtonLevel1Obfuscate))
              .addGroup(jPanelLowerLayout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addComponent(jRadioButtonLevel2Obfuscate)
              .addGroup(jPanelLowerLayout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addComponent(jRadioButtonLevel3Obfuscate))))
            .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
          .addGroup(jPanelLowerLayout.createSequentialGroup()
            .addGroup(jPanelLowerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
              .addGroup(jPanelLowerLayout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(jPanelLowerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                  .addComponent(jLabelGetFileToObfuscate, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE)
                  .addComponent(jButtonGetLuaFile, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                  .addComponent(jRadioButtonLevel0Obfuscate))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanelLowerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                  .addComponent(jButtonGetLuaFolder, javax.swing.GroupLayout.DEFAULT_SIZE, 160, Short.MAX_VALUE)
                  .addComponent(jButtonGetAST, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, Short.MAX_VALUE)
                .addGroup(jPanelLowerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                  .addComponent(jButtonGetBlackListFile, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                  .addComponent(jButtonDestinationDirectory, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
              .addGroup(jPanelLowerLayout.createSequentialGroup()
                .addGap(186, 186, 186)
                .addGroup(jPanelLowerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                  .addComponent(jLabelImportLuaFolder, javax.swing.GroupLayout.PREFERRED_SIZE, 176, javax.swing.GroupLayout.PREFERRED_SIZE)
                  .addGroup(jPanelLowerLayout.createSequentialGroup()
                    .addComponent(jLabelPart2ofGetLuaFolderLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 147, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                    .addComponent(jLabelGetBlackListFile, javax.swing.GroupLayout.PREFERRED_SIZE, 147, javax.swing.GroupLayout.PREFERRED_SIZE))))
              .addGroup(jPanelLowerLayout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addComponent(jButtonObfuscate, javax.swing.GroupLayout.PREFERRED_SIZE, 157, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanelLowerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                  .addGroup(jPanelLowerLayout.createSequentialGroup()
                    .addComponent(jButtonHelp, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(18, 18, 18)
                    .addComponent(jButtonExitProgram, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                  .addComponent(jTextFieldMakeOutputFile))))
            .addGap(31, 31, 31))))
      .addGroup(jPanelLowerLayout.createSequentialGroup()
        .addContainerGap()
        .addGroup(jPanelLowerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
          .addComponent(jLabelDeveloperCredits, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
          .addComponent(jLabelBottomIconImage, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        .addContainerGap())
    );
    jPanelLowerLayout.setVerticalGroup(
      jPanelLowerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGroup(jPanelLowerLayout.createSequentialGroup()
        .addContainerGap()
        .addGroup(jPanelLowerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
          .addComponent(jLabelLuaIcon, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
          .addGroup(jPanelLowerLayout.createSequentialGroup()
            .addGroup(jPanelLowerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
              .addGroup(jPanelLowerLayout.createSequentialGroup()
                .addComponent(jLabelObfuscationOptions)
                .addGroup(jPanelLowerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                  .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelLowerLayout.createSequentialGroup()
                    .addGroup(jPanelLowerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                      .addGroup(jPanelLowerLayout.createSequentialGroup()
                        .addComponent(jLabelGetBlackListFile)
                        .addGap(9, 9, 9))
                      .addGroup(jPanelLowerLayout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(jLabelImportLuaFolder)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabelPart2ofGetLuaFolderLabel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)))
                    .addGroup(jPanelLowerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                      .addComponent(jButtonGetBlackListFile)
                      .addComponent(jButtonGetLuaFolder)))
                  .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelLowerLayout.createSequentialGroup()
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabelGetFileToObfuscate)
                    .addGap(9, 9, 9)
                    .addComponent(jButtonGetLuaFile)))
                .addGap(18, 18, 18)
                .addComponent(jRadioButtonLevel0Obfuscate))
              .addGroup(jPanelLowerLayout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addGroup(jPanelLowerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                  .addComponent(jButtonDestinationDirectory)
                  .addComponent(jButtonGetAST))))
            .addGap(18, 18, 18)
            .addComponent(jRadioButtonLevel1Obfuscate)
            .addGap(18, 18, 18)
            .addComponent(jRadioButtonLevel2Obfuscate)
            .addGap(18, 18, 18)
            .addComponent(jRadioButtonLevel3Obfuscate)
            .addGap(68, 68, 68)
            .addGroup(jPanelLowerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
              .addComponent(jTextFieldMakeOutputFile, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
              .addComponent(jButtonObfuscate))
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addGroup(jPanelLowerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
              .addComponent(jButtonExitProgram)
              .addComponent(jButtonHelp))
            .addGap(25, 25, 25)))
        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
        .addComponent(jLabelDeveloperCredits, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
        .addComponent(jLabelBottomIconImage, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
        .addContainerGap())
    );
    
    ButtonGroup radio_buttons = new ButtonGroup();
    radio_buttons.add(jRadioButtonLevel0Obfuscate);
    radio_buttons.add(jRadioButtonLevel1Obfuscate);
    radio_buttons.add(jRadioButtonLevel2Obfuscate);
    radio_buttons.add(jRadioButtonLevel3Obfuscate);
    jRadioButtonLevel0Obfuscate.setSelected(true);
    
    jLabelLuaGuardObfuscatorv10.setFont(new java.awt.Font("Malayalam MN", 0, 36)); // NOI18N
    jLabelLuaGuardObfuscatorv10.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
    jLabelLuaGuardObfuscatorv10.setText("LuaGuard Obfuscator v1.0");
    jLabelLuaGuardObfuscatorv10.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 51, 204), 4));

    javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
    getContentPane().setLayout(layout);
    layout.setHorizontalGroup(
      layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addComponent(jLabelLuaGuardObfuscatorv10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
      .addComponent(jPanelLower, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
    );
    layout.setVerticalGroup(
      layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGroup(layout.createSequentialGroup()
        .addComponent(jLabelLuaGuardObfuscatorv10, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
        .addComponent(jPanelLower, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
    );

    pack();
  }// </editor-fold>//GEN-END:initComponents

  private void jButtonExitProgramActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonExitProgramActionPerformed
    System.out.println("Exit Button Pressed");
    System.exit(0);
  }//GEN-LAST:event_jButtonExitProgramActionPerformed

  //////////////////////////////////////////////////////////////////////////////
  private void jButtonGetLuaFileActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonGetLuaFileActionPerformed
    System.out.println("Get file pressed");

    this.arrayList_Lua_Input_Files.clear();

    int returnVal = jFileChooserGetLuaFile.showOpenDialog(this);
    if (returnVal == JFileChooser.APPROVE_OPTION) {
      this.arrayList_Lua_Input_Files.add(jFileChooserGetLuaFile.getSelectedFile()
              .getAbsolutePath());

      inputPaths.add(jFileChooserGetLuaFile.getSelectedFile().getAbsolutePath());
      System.out.println(("The File Name Selected is: "
              + jFileChooserGetLuaFile.getSelectedFile().getName()));

    } else {
      System.out.println("File access cancelled by user.");
    }
  }//GEN-LAST:event_jButtonGetLuaFileActionPerformed

  //////////////////////////////////////////////////////////////////////////////
  private void jTextFieldMakeOutputFileActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldMakeOutputFileActionPerformed
  }//GEN-LAST:event_jTextFieldMakeOutputFileActionPerformed

  //////////////////////////////////////////////////////////////////////////////
  private void jFileChooserGetLuaFileActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jFileChooserGetLuaFileActionPerformed
  }//GEN-LAST:event_jFileChooserGetLuaFileActionPerformed

  //////////////////////////////////////////////////////////////////////////////
  public static String execCmd(String cmd) throws java.io.IOException {
    Process proc = Runtime.getRuntime().exec(cmd);
    java.io.InputStream is = proc.getInputStream();
    java.util.Scanner s = new java.util.Scanner(is).useDelimiter("\\A");
    String val = "";
    if (s.hasNext()) {
      val = s.next();
    } else {
      val = "";
    }
    return val;
  }

  //////////////////////////////////////////////////////////////////////////////
  public String returnFilenamePartFromFullPath(String stringFullPathInput) {
    // gets filename without extension
    int intDotIndex = stringFullPathInput.lastIndexOf(".");
    int intSeparatorIndex = stringFullPathInput.lastIndexOf(File.separator);
    return stringFullPathInput.substring(intSeparatorIndex + 1, intDotIndex);
  }

  //////////////////////////////////////////////////////////////////////////////
  public String returnDirectoryFromFullPath(String stringFullPathInput) {
    // gets directory from full path

    return stringFullPathInput.substring(0, stringFullPathInput.lastIndexOf(File.separator));
  }

  //////////////////////////////////////////////////////////////////////////////
  public String returnCurrentWorkingDirectory() {
    File fileCurrentDirectory = new File(".");

    String stringCanonicalPathCurrent = "";

    try {
      stringCanonicalPathCurrent
              = fileCurrentDirectory.getCanonicalPath();
    } catch (IOException ex) {
      Logger.getLogger(GUI_LuaGuard.class.getName()).log(Level.SEVERE, null, ex);
    }

    return stringCanonicalPathCurrent;
  }

  private String getSeperatedList(ArrayList<String> list){
	  String s = "";
	  for(int i = 0; i < list.size(); ++i){
		  s += list.get(i);
		  if(i < list.size()-1)
			  s += File.separator;
	  }
	  return s;
  }
  //////////////////////////////////////////////////////////////////////////////
  private void jButtonObfuscateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonObfuscateActionPerformed

	  //error checking
	  try{
		  ArrayList<String> blackList = UTIL.getBlacklist(blackListFile);
		  if(inputPaths.size() > 0){
			  if(astDir != null && outputDir != null){
				  String input = getSeperatedList(inputPaths);
				  UTIL.driver(obfuscationLevel, UTIL.seperatedFilesToList(input, outputDir, false), UTIL.seperatedFilesToList(input, astDir, true), blackList);
			  }
		  }

	  }
	  catch (FileNotFoundException ex){
		  ex.printStackTrace();
	  }
	  catch (SecurityException ex){
		  ex.printStackTrace();
	  }
	  
    for (int intInputFileIndex = 0; intInputFileIndex < arrayList_Lua_Input_Files.size(); intInputFileIndex++) {

      File f;

      try {
        String stringInputFileFullPath = arrayList_Lua_Input_Files.get(intInputFileIndex);

        String stringInputDirectory = returnDirectoryFromFullPath(stringInputFileFullPath);

      } catch (Exception exceptionReference) {
        exceptionReference.toString();
      }
    }
  }//GEN-LAST:event_jButtonObfuscateActionPerformed

  public void getClassFolders(File file, ArrayList<String> fileNames) {
    for (File child : file.listFiles()) {
      if (child.isDirectory()) {
        getClassFolders(child, fileNames);
      } else if (child.getName().endsWith(".lua") || child.getName().endsWith(".Lua")) {
        fileNames.add(child.getAbsolutePath());

      }
    }
  }

  private void jButtonGetLuaFolderActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonGetLuaFolderActionPerformed
    System.out.println("Get Lua Folder pressed");
    this.arrayList_Lua_Input_Files.clear();
    int returnVal = jFileChooserGetLuaFolder.showOpenDialog(this);

    if (returnVal == JFileChooser.APPROVE_OPTION) {
      File fileSelectedDirectory = jFileChooserGetLuaFolder.getSelectedFile();
      inputPaths.add(jFileChooserGetLuaFolder.getSelectedFile().getAbsolutePath());
      System.out.println("HERE");
      System.out.println(jFileChooserGetLuaFolder.getSelectedFile());

      getClassFolders(fileSelectedDirectory, arrayList_Lua_Input_Files);

    } else {
      System.out.println("File access cancelled by user.");
    }
  }//GEN-LAST:event_jButtonGetLuaFolderActionPerformed

  private void jFileChooserGetLuaFolderActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jFileChooserGetLuaFolderActionPerformed
    // Just a Jlabel.
  }//GEN-LAST:event_jFileChooserGetLuaFolderActionPerformed

  private void jButtonGetBlackListFileActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonGetBlackListFileActionPerformed

    arrayList_String_Blacklist.clear();
    System.out.println("Get Black List File pressed");

    int returnVal = jFileChooserGetBlackListFile.showOpenDialog(this);
    if (returnVal == JFileChooser.APPROVE_OPTION) {
      blackListFile = jFileChooserGetBlackListFile.getSelectedFile();

      System.out.println("The Black List File Selected is: " + blackListFile);

    } else {
      System.out.println("File access cancelled by user.");
    }

  }//GEN-LAST:event_jButtonGetBlackListFileActionPerformed

  private void jRadioButtonLevel3ObfuscateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioButtonLevel0ObfuscateActionPerformed
	  if (jRadioButtonLevel3Obfuscate.isSelected()) {
		  System.out.println("Level 3 Obfuscation CheckBox Selected");
		  obfuscationLevel = 3;
	  } else {
		  System.out.println("Level 3 Obfuscation CheckBox Unchecked");
	  }
  }//GEN-LAST:event_jRadioButtonLevel3ObfuscateActionPerformed
  private void jRadioButtonLevel0ObfuscateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioButtonLevel0ObfuscateActionPerformed
    if (jRadioButtonLevel0Obfuscate.isSelected()) {
      System.out.println("Level 0 Obfuscation CheckBox Selected");
      obfuscationLevel = 0;
    } else {
      System.out.println("Level 0 Obfuscation CheckBox Unchecked");
    }
  }//GEN-LAST:event_jRadioButtonLevel0ObfuscateActionPerformed

  private void jRadioButtonLevel1ObfuscateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioButtonLevel1ObfuscateActionPerformed
    if (jRadioButtonLevel1Obfuscate.isSelected()) {
      System.out.println("Level 1 Obfuscation CheckBox Selected");
      obfuscationLevel = 1;
    } else {
      System.out.println("Level 1 Obfuscation CheckBox Unchecked");
    }
  }//GEN-LAST:event_jRadioButtonLevel1ObfuscateActionPerformed

  private void jRadioButtonLevel2ObfuscateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioButtonLevel2ObfuscateActionPerformed
    if (jRadioButtonLevel2Obfuscate.isSelected()) {
      System.out.println("Level 2 Obfuscation CheckBox Selected");
      obfuscationLevel = 2;
    } else {
      System.out.println("Level 2 Obfuscation CheckBox Unchecked");
    }
  }//GEN-LAST:event_jRadioButtonLevel2ObfuscateActionPerformed

  private void jButtonDestinationDirectoryActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonDestinationDirectoryActionPerformed
    System.out.println("Destination Directory Pressed");

    int returnValOfDestFolder = jFileChooserGetLuaFolder.showOpenDialog(this);

    if (returnValOfDestFolder == JFileChooser.APPROVE_OPTION) {
      outputDir = jFileChooserGetLuaFolder.getSelectedFile();
      System.out.println("The Destination Directory is :" + outputDir);

    } else {
      System.out.println("File access cancelled by user.");
    }

  }//GEN-LAST:event_jButtonDestinationDirectoryActionPerformed

  private void jButtonGetASTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonGetASTActionPerformed
    System.out.println("GetAST Pressed");

    int returnValOfAST = jFileChooserGetLuaFolder.showOpenDialog(this);
    if (returnValOfAST == JFileChooser.APPROVE_OPTION) {
      astDir = jFileChooserGetLuaFolder.getSelectedFile();
      System.out.println("The AST is : " + astDir);
    } else {
      System.out.println("File access cancelled by user.");

    }
  }//GEN-LAST:event_jButtonGetASTActionPerformed

  /**
   * @param args the command stringLineOfText arguments
   */
  public static void main(String args[]) {
    /* Set the Nimbus look and feel */
    //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) inputStreamReference not available, stay with the default look and feel.
     * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html
     */
    try {
      for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
        if ("Nimbus".equals(info.getName())) {
          javax.swing.UIManager.setLookAndFeel(info.getClassName());
          break;

        }
      }
    } catch (ClassNotFoundException ex) {
      java.util.logging.Logger.getLogger(GUI_LuaGuard.class
              .getName()).log(java.util.logging.Level.SEVERE, null, ex);
    } catch (InstantiationException ex) {
      java.util.logging.Logger.getLogger(GUI_LuaGuard.class
              .getName()).log(java.util.logging.Level.SEVERE, null, ex);
    } catch (IllegalAccessException ex) {
      java.util.logging.Logger.getLogger(GUI_LuaGuard.class
              .getName()).log(java.util.logging.Level.SEVERE, null, ex);
    } catch (javax.swing.UnsupportedLookAndFeelException ex) {
      java.util.logging.Logger.getLogger(GUI_LuaGuard.class
              .getName()).log(java.util.logging.Level.SEVERE, null, ex);
    }
    //</editor-fold>

    /* Create and display the form */
    java.awt.EventQueue.invokeLater(new Runnable() {
      public void run() {
        new GUI_LuaGuard().setVisible(true);
      }
    });
  }

  private ArrayList<String> inputPaths = new ArrayList<String>();
  private File blackListFile = null;
  private File astDir = null;
  private File outputDir = null;
  private int obfuscationLevel = 0;
  // Variables declaration - do not modify//GEN-BEGIN:variables
  private javax.swing.JButton jButton4;
  private javax.swing.JButton jButtonDestinationDirectory;
  private javax.swing.JButton jButtonExitProgram;
  private javax.swing.JButton jButtonGetAST;
  private javax.swing.JButton jButtonGetBlackListFile;
  private javax.swing.JButton jButtonGetLuaFile;
  private javax.swing.JButton jButtonGetLuaFolder;
  private javax.swing.JButton jButtonHelp;
  private javax.swing.JButton jButtonObfuscate;
  private javax.swing.JRadioButton jRadioButtonLevel3Obfuscate;
  private javax.swing.JRadioButton jRadioButtonLevel0Obfuscate;
  private javax.swing.JRadioButton jRadioButtonLevel1Obfuscate;
  private javax.swing.JRadioButton jRadioButtonLevel2Obfuscate;
  private javax.swing.JFileChooser jFileChooserGetBlackListFile;
  private javax.swing.JFileChooser jFileChooserGetLuaFile;
  private javax.swing.JFileChooser jFileChooserGetLuaFolder;
  private javax.swing.JLabel jLabel8;
  private javax.swing.JLabel jLabelBottomIconImage;
  private javax.swing.JLabel jLabelDeveloperCredits;
  private javax.swing.JLabel jLabelGetBlackListFile;
  private javax.swing.JLabel jLabelGetFileToObfuscate;
  private javax.swing.JLabel jLabelImportLuaFolder;
  private javax.swing.JLabel jLabelLuaGuardObfuscatorv10;
  private javax.swing.JLabel jLabelLuaIcon;
  private javax.swing.JLabel jLabelObfuscationOptions;
  private javax.swing.JLabel jLabelPart2ofGetLuaFolderLabel;
  private javax.swing.JPanel jPanelLower;
  private javax.swing.JTextField jTextFieldMakeOutputFile;
  // End of variables declaration//GEN-END:variables
}