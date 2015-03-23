package paoo.cappuccino.ihm.newCompany;

import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import paoo.cappuccino.ihm.util.ErrorableTextField;
import paoo.cappuccino.ihm.util.IhmConstants;

/**
 * View for the registration Gui.
 *
 * @author Opsomer Mathias
 */
@SuppressWarnings("serial")
public class NewCompanyView extends JPanel implements ChangeListener {

  private NewCompanyModel model;
  private ErrorableTextField CompanyNamePanel;
  private ErrorableTextField numerPanel;
  private ErrorableTextField postCodePanel;
  private ErrorableTextField boxPanel;
  private ErrorableTextField cityPanel;
  private ErrorableTextField streetPanel;



  public NewCompanyView(NewCompanyModel model, JTextField companyNameField, JTextField numerField,
      JTextField postCodeField, JTextField streetField, JTextField cityField, JTextField boxField) {
    super(new GridLayout(7, 1));
    this.setBorder(new EmptyBorder(0, IhmConstants.XL_GAP, 0, IhmConstants.XL_GAP));

    this.model = model;

    this.CompanyNamePanel = new ErrorableTextField(companyNameField, "Nom : ");
    this.add(this.CompanyNamePanel);

    this.add(new JLabel("Adresse : "));

    this.streetPanel = new ErrorableTextField(streetField, "Rue : ");
    this.add(this.streetPanel);

    this.cityPanel = new ErrorableTextField(cityField, "Ville : ");
    this.add(this.cityPanel);

    this.boxPanel = new ErrorableTextField(boxField, "Boite : ");
    this.add(this.boxPanel);

    this.numerPanel = new ErrorableTextField(numerField, "Numéro : ");
    this.add(this.numerPanel);

    this.postCodePanel = new ErrorableTextField(postCodeField, "Code postal : ");
    this.add(this.postCodePanel);



    model.addChangeListener(this);
    stateChanged(null);
  }

  @Override
  public void stateChanged(ChangeEvent event) {
    CompanyNamePanel.setError(model.getCompanyNameError());
    streetPanel.setError(model.getStreetError());
    cityPanel.setError(model.getCityError());
    boxPanel.setError(model.getBoxError());
    numerPanel.setError(model.getNumerError());
    postCodePanel.setError(model.getPostCodeError());

  }
}
