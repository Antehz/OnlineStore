package by.hrychanok.training.shop.web.page.product;

import java.util.Arrays;
import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeAction;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.model.IModel;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.apache.wicket.validation.validator.RangeValidator;

import com.googlecode.wicket.kendo.ui.form.TextField;
import by.hrychanok.training.shop.model.UserRole;
import by.hrychanok.training.shop.model.Wheel;
import by.hrychanok.training.shop.model.WheelType;
import by.hrychanok.training.shop.service.GenericProductService;
import by.hrychanok.training.shop.web.app.AuthorizedSession;
import by.hrychanok.training.shop.web.page.common.WheelTypeChoiceRenderer;

@AuthorizeAction(roles = { "admin" }, action = "ENABLE")
public class WheelFeatures extends ProductFormPanel<Wheel> {

	@SpringBean
	GenericProductService<Wheel> genericProductService;

	boolean visibleForOnlyAdmin = AuthorizedSession.get().isSignedIn()
			&& AuthorizedSession.get().getLoggedUser().getRole().equals(UserRole.admin);

	private Wheel wheel;

	public WheelFeatures(String id, IModel<Wheel> model) {
		super(id, model);
		wheel = (Wheel) model.getObject();
	}

	@Override
	protected void onInitialize() {
		super.onInitialize();

		// DropDownChoice wheel type //
		final DropDownChoice<WheelType> wheelType = new DropDownChoice<>("wheelType", Arrays.asList(WheelType.values()),
				WheelTypeChoiceRenderer.INSTANCE);
		wheelType.setRequired(true);
		add(wheelType);

		TextField<Integer> rimSize = new TextField<>("rimSize");
		rimSize.add(RangeValidator.<Integer> range(13, 22));
		rimSize.setRequired(true);
		add(rimSize);

		TextField<Integer> rimWidth = new TextField<>("rimWidth");
		rimWidth.add(RangeValidator.<Integer> range(4, 45));
		rimWidth.setRequired(true);
		add(rimWidth);

		TextField<Integer> numberHole = new TextField<>("numberHole");
		numberHole.add(RangeValidator.<Integer> range(3, 10));
		numberHole.setRequired(true);
		add(numberHole);

		TextField<Integer> pcd = new TextField<>("pcd");
		pcd.add(RangeValidator.<Integer> range(13, 205));
		pcd.setRequired(true);
		add(pcd);
		
		TextField<Integer> dia = new TextField<>("dia");
		dia.add(RangeValidator.<Integer> range(35, 161));
		dia.setRequired(true);
		add(dia);
		
		TextField<Integer> et = new TextField<>("et");
		et.add(RangeValidator.<Integer> range(-5, 124));
		et.setRequired(true);
		add(et);
		
	}
}
