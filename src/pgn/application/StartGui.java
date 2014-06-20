package pgn.application;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.File;

/**
 * Created with IntelliJ IDEA.
 * User: BamBalooon
 * Date: 08.06.14
 * Time: 23:32
 * To change this template use File | Settings | File Templates.
 */
public class StartGui extends PgnGui {
    public static final String FILE_CHOOSE = "FILE_CHOOSE";

    protected File pgnFile;
    private JLabel label;
    private JLabel tokenizerLabel;

    public StartGui(PGNtranslator application) {
        super(application);
        JPanel main = new JPanel();
        JButton fileChooser = new JButton();
        fileChooser.addActionListener(this);
        fileChooser.setActionCommand(StartGui.FILE_CHOOSE);
        fileChooser.setText("Wybierz plik...");
        label = new JLabel();
        label.setText("Wybierz plik '.pgn', który chcesz przetworzyć.");
        //tokenizerLabel = newJLabel();
        //tokenizerLabel.setText("Wynik .");
        main.add(fileChooser);
        main.add(label);
        

        JToolBar toolBar = new JToolBar();
        toolBar.add(nextWindowBtn);
        nextWindowBtn.setVisible(false);

        add(main, BorderLayout.CENTER);
        add(toolBar, BorderLayout.PAGE_END);
        pack();
    }

    public void actionPerformed(ActionEvent e) {
        super.actionPerformed(e);
        switch (e.getActionCommand()) {
            case FILE_CHOOSE:
                JFileChooser fileChooser = new JFileChooser();
                FileNameExtensionFilter filter = new FileNameExtensionFilter("PGN files", "pgn");
                fileChooser.setFileFilter(filter);
                int returnValue = fileChooser.showOpenDialog(null);
                if(returnValue == JFileChooser.APPROVE_OPTION) {
                    pgnFile = fileChooser.getSelectedFile();
                    application.setPgnFile(pgnFile);
                    label.setText("Plik: "+pgnFile.getName()+" załadowany! Przejdź do następnego kroku...");
                    nextWindowBtn.setVisible(true);
                    application.createTokenizer();
                }
                break;
        }
    }
}
