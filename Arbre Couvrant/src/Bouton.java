import javax.swing.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;

public class Bouton extends JButton implements MouseListener{
    BoutonEnum bEnum;

    public Bouton(String str, BoutonEnum bEnum){
        super(str);
        this.bEnum = bEnum;
        this.addMouseListener(this);
    }

    @Override
    public void mouseClicked(MouseEvent event) {
    }

    @Override
    public void mousePressed(MouseEvent event) {
        try {
            Main.fenetre.changeContent(bEnum);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void mouseReleased(MouseEvent event) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}