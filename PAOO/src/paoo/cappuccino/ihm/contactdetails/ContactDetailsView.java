package paoo.cappuccino.ihm.contactdetails;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import paoo.cappuccino.business.dto.ICompanyDto;
import paoo.cappuccino.business.dto.IContactDto;
import paoo.cappuccino.ihm.menu.MenuEntry;
import paoo.cappuccino.ihm.menu.MenuModel;
import paoo.cappuccino.ihm.util.JLabelFont;
import paoo.cappuccino.ucc.ICompanyUcc;

@SuppressWarnings("serial")
public class ContactDetailsView extends JPanel implements ChangeListener {

  private final ContactDetailsModel model;
  private final JLabel invalidMessagePanel = new JLabel("Invalide !");

  public ContactDetailsView(ContactDetailsModel model, JButton button, ICompanyUcc companyUcc,
                            MenuModel menu) {
    super(new BorderLayout());

    this.model = model;
    this.invalidMessagePanel.setForeground(Color.RED);

    final IContactDto contact = model.getContactDto();

    final JPanel contactWrapperPanel = new JPanel(new BorderLayout());
    contactWrapperPanel.add(new JLabelFont(contact.getLastName() + " " + contact.getFirstName(), 20),
                            BorderLayout.NORTH);

    final JPanel contactPanel = new JPanel(new GridLayout(0, 3));
    contactWrapperPanel.add(contactPanel);

    contactPanel.add(new JLabel("Nom : "));
    contactPanel.add(new JLabel(contact.getLastName()));
    contactPanel.add(new JPanel());

    contactPanel.add(new JLabel("Prénom : "));
    contactPanel.add(new JLabel(contact.getFirstName()));
    contactPanel.add(new JPanel());

    contactPanel.add(new JLabel("Mail : "));
    contactPanel.add(new JLabel(contact.getEmail() == null ? "N/A" : contact.getEmail()));
    final JPanel markInvalidPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
    markInvalidPanel.add(button);
    markInvalidPanel.add(invalidMessagePanel);
    contactPanel.add(markInvalidPanel);

    contactPanel.add(new JLabel("Téléphone : "));
    contactPanel.add(new JLabel(contact.getPhone() == null ? "N/A" : contact.getPhone()));
    contactPanel.add(new JPanel());

    final ICompanyDto contactCompany = companyUcc.getCompanyById(contact.getCompany());
    final JLabel companyLabel = new JLabel(contactCompany.getName());
    companyLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    companyLabel.addMouseListener(new MouseAdapter() {
      @Override
      public void mouseClicked(MouseEvent e) {
        menu.setCurrentPage(MenuEntry.COMPANY_DETAILS, contactCompany);
      }
    });
    contactPanel.add(new JLabel("Entreprise : "));
    contactPanel.add(companyLabel);
    contactPanel.add(new JPanel());

    this.add(contactWrapperPanel);

    this.model.addChangeListener(this);

    button.addActionListener(e -> stateChanged(null));
    stateChanged(null);
  }

  @Override
  public void stateChanged(ChangeEvent event) {
    this.invalidMessagePanel.setVisible(!model.getContactDto().isEmailValid());
  }
}
