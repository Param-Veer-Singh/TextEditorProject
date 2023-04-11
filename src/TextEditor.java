import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.undo.UndoManager;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

public class TextEditor implements ActionListener  {
    //Declaring properties of text editor
    JFrame frame;
    JTextArea textArea;
    JMenuBar menubar;
    UndoManager manager; //this will helps in undo and redo operations
    JMenu file,edit;
    JMenuItem New_File, Save_File, Open_File; //file menu items
    JMenuItem cut, copy, paste, undo, redo, selectAll, close; // edit menu items
    TextEditor(){

        //Initialize frame
        frame = new JFrame();

        //Initialize menubar
        menubar = new JMenuBar();
        //Initialize undo manager
        manager =  new UndoManager();
        //Initialize text area
        textArea = new JTextArea();
        //Add undoable edit listener to text area
        textArea.getDocument().addUndoableEditListener(manager);

        //Initialize menus
        file = new JMenu("File");
        edit = new JMenu("Edit");

        //Initialize file menu items
        New_File = new JMenuItem("New File");
        Open_File = new JMenuItem("Open File");
        Save_File = new JMenuItem("Save File");

        //Adding action listener to file menu items
        New_File.addActionListener(this);
        Open_File.addActionListener(this);
        Save_File.addActionListener(this);

        //Initialize edit menu items
        cut = new JMenuItem("Cut");
        copy = new JMenuItem("Copy");
        paste = new JMenuItem("Paste");
        undo = new JMenuItem("Undo");
        redo = new JMenuItem("Redo");
        selectAll = new JMenuItem("Select All");
        close = new JMenuItem("Close");

        //Adding action listener to edit menu items
        cut.addActionListener(this);
        copy.addActionListener(this);
        paste.addActionListener(this);
        undo.addActionListener(this);
        redo.addActionListener(this);
        selectAll.addActionListener(this);
        close.addActionListener(this);

        //Add menu items to file menu
        file.add(New_File);
        file.add(Open_File);
        file.add(Save_File);

        //Add menu items to edit menu
        edit.add(cut);
        edit.add(copy);
        edit.add(paste);
        edit.add(undo);
        edit.add(redo);
        edit.add(selectAll);
        edit.add(close);

        //Add menus to menubar
        menubar.add(file);
        menubar.add(edit);

        //Add menubar to frame
        frame.setJMenuBar(menubar);

        //create content panel
        JPanel panel = new JPanel();
        panel.setBorder(new EmptyBorder(5,5,5,5));
        panel.setLayout(new BorderLayout(0,0));
        //Add text area to panel
        panel.add(textArea, BorderLayout.CENTER);
        //create scroll pane
        JScrollPane scrollPane = new JScrollPane(textArea, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        //Add scroll pane to panel
        panel.add(scrollPane);
        //Add panel to frame
        frame.add(panel);
        //Set dimensions of frame
        frame.setBounds(400,150,500,500);
        //Add name to application
        frame.setTitle("Text Editor");
        //Set visibility true;
        frame.setVisible(true);

        frame.setLayout(null);
    }
    @Override
    public void actionPerformed(ActionEvent actionEvent){
        if(actionEvent.getSource() == cut){
            textArea.cut();
        }else if(actionEvent.getSource() == copy){
            textArea.copy();
        }else if(actionEvent.getSource() == paste){
            textArea.paste();
        }
        else if(actionEvent.getSource() == selectAll){
            textArea.selectAll();
        }else if(actionEvent.getSource() == close){
            System.exit(0);
        }else if(actionEvent.getSource() == Open_File){
            //open file chooser
            JFileChooser fileChooser = new JFileChooser("C:");
            int chooseOption = fileChooser.showOpenDialog(null);
            //if we have clicked on open button
            if(chooseOption == JFileChooser.APPROVE_OPTION){
                //get selected file
                File file = fileChooser.getSelectedFile();
                //get path of selected file
                String filepath = file.getPath();
                try{
                    //Initialize file reader
                    FileReader fileReader = new FileReader(filepath);
                    //Initialize buffered reader
                    BufferedReader bufferedReader = new BufferedReader(fileReader);
                    String intermediate = "", output = "";
                    //Read contents of file line by line
                    while((intermediate = bufferedReader.readLine()) != null){
                        output += intermediate;
                    }
                    //set output string to text area
                    textArea.setText(output);
                }
                catch(IOException ioException){
                    ioException.printStackTrace();
                }
            }
        }
        else if(actionEvent.getSource() == Save_File){
            //open file chooser
            JFileChooser fileChooser = new JFileChooser("C:");
            int chooseOption = fileChooser.showSaveDialog(null);
            //if we clicked on save button
            if(chooseOption == JFileChooser.APPROVE_OPTION){
                //create a new file with choosen path
                File file = new File(fileChooser.getSelectedFile().getAbsolutePath()+".txt");
                try{
                    //Initialize file writer
                    FileWriter fileWriter = new FileWriter(file);
                    //Initialize buffered writer
                    BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
                    //write contents of text area to file
                    textArea.write(bufferedWriter);
                    bufferedWriter.close();
                }
                catch(IOException ioException){
                    ioException.printStackTrace();
                }
            }
        }
        else if(actionEvent.getSource() == New_File){
            TextEditor newTextEditor = new TextEditor();
        }
        else if(actionEvent.getSource() == undo){
            try{
                manager.undo();
            }
            catch(Exception exception){

            }
        }else if(actionEvent.getSource() == redo){
            try{
                manager.redo();
            }
            catch(Exception exception){

            }
        }
    }
    public static void main(String[] args) {
        TextEditor texteditor = new TextEditor();
    }
}
