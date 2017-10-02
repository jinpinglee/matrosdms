 
package net.schwehla.matrosdms.rcp.parts.masterdata;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.eclipse.e4.ui.workbench.modeling.ESelectionService;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.custom.StackLayout;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;

import net.schwehla.matrosdms.domain.core.InfoBaseElement;
import net.schwehla.matrosdms.domain.core.InfoContext;
import net.schwehla.matrosdms.domain.util.Identifier;
import net.schwehla.matrosdms.persistenceservice.IMatrosServiceService;
import net.schwehla.matrosdms.rcp.swt.squarebutton.SquareButton;
import net.schwehla.matrosdms.rcp.swt.squarebutton.SquareButtonGroup;
import org.eclipse.swt.widgets.Label;

public class ElementPart {
	
	@Inject
	public ElementPart() {

	}

	
	@Inject IMatrosServiceService matrosService;
	

	Composite switchTo = null;
	
	SquareButtonGroup sg = new SquareButtonGroup();

	InfoBaseElement baseelement;

	@Inject
	ESelectionService selectionService;


	ToolItem btnCommons;
	Composite compositeContent ;
	StackLayout sl_compositeContentArea = new StackLayout();
	
	List<ToolItem> toolItemList = new ArrayList<>();


	Composite compositeRoot;
	
	
	InfoContext _localDropfieldContext;
	private Table table;

	@PostConstruct
	public void postConstruct(Composite parent) {
		
		
		_localDropfieldContext = new InfoContext(Identifier.createNEW(), "dummy");
		
		parent.setLayout(new GridLayout(1, false));
		

		compositeRoot = new Composite(parent, SWT.NONE);
		compositeRoot.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, true, 1, 1));
		
		
		GridLayout gl_composite = new GridLayout(1, false);
		gl_composite.horizontalSpacing = 0;
		compositeRoot.setLayout(gl_composite);

		Composite compositeButtons = new Composite(compositeRoot, SWT.NONE);
	
		
		buildSquareButton("wer", compositeButtons);
		
		buildSquareButton("was", compositeButtons);

		buildSquareButton("wo", compositeButtons);

		buildSquareButton("art", compositeButtons);

		
		buildSquareButton("Attribute", compositeButtons);
		
		buildSquareButton("Originalstore", compositeButtons);

		buildSquareButton("User", compositeButtons);

		buildSquareButton("Roles", compositeButtons);

		buildSquareButton("DBConfig", compositeButtons);

		buildSquareButton("Clientconfig", compositeButtons);
		compositeButtons.setLayout(new GridLayout(1, false));

	//	sg.setCurrentlyToggledButton(button);
	

	}
	


	private SquareButton buildSquareButton(String name, Composite parent) {
		
		SquareButton.SquareButtonBuilder builder = new SquareButton.SquareButtonBuilder();
	    builder .setParent(parent)
	    	.setText(name)
	    	.setCornerRadius(3).setToggleable(true)
	        .setDefaultMouseClickAndReturnKeyHandler(new SquareButton.ButtonClickHandler() {
	          @Override
	          public void clicked() {
	           // openTestDialog(shell);
	          }
	        });
	    
	    SquareButton b = builder.build();
	    
		b.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		
	    
	    sg.addButton(b);
	    
	    return b;
	    
	}

	private void appendButton(ToolBar compositeButtons, String string) {
		
		ToolItem btnIdentifier = new ToolItem(compositeButtons, SWT.RADIO);
		btnIdentifier.setText(string);
		toolItemList.add(btnIdentifier);
		
		
	}

}