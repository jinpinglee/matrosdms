package net.schwehla.matrosdms.rcp.swt;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ComboViewer;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;

public class TypedComboBox<T> {

    public ComboViewer getViewer() {
		return viewer;
	}



	private ComboViewer viewer;
    private TypedComboBoxLabelProvider<T> labelProvider;
    private List<T> content;
    private List<TypedComboBoxSelectionListener<T>> selectionListeners;
    private T currentSelection;

    public TypedComboBox(Composite parent) {
        this.viewer = new ComboViewer(parent, SWT.READ_ONLY);
        this.viewer.setContentProvider(ArrayContentProvider.getInstance());

        viewer.setLabelProvider(new LabelProvider() {
            @Override
            public String getText(Object element) {
                T typedElement = getTypedObject(element);
                if (labelProvider != null && typedElement != null) {
                    if (typedElement == currentSelection) {
                        return labelProvider.getSelectedLabel(typedElement);
                    } else {
                        return labelProvider.getListLabel(typedElement);
                    }

                } else {
                    return element.toString();
                }
            }
        });

        viewer.addSelectionChangedListener(new ISelectionChangedListener() {
            @Override
            public void selectionChanged(SelectionChangedEvent event) {
                IStructuredSelection selection = (IStructuredSelection) event
                        .getSelection();
                T typedSelection = getTypedObject(selection.getFirstElement());
                if (typedSelection != null) {
                    currentSelection = typedSelection;
                    viewer.refresh();
                    notifySelectionListeners(typedSelection);
                }

            }
        });

        this.content = new ArrayList<T>();
        this.selectionListeners = new ArrayList<TypedComboBoxSelectionListener<T>>();
    }

    public void setLabelProvider(TypedComboBoxLabelProvider<T> labelProvider) {
        this.labelProvider = labelProvider;
    }

    public void setContent(List<T> content) {
        this.content = content;
        this.viewer.setInput(content.toArray());
    }

    public List<T> getContent() {
		return content;
	}

	public T getSelection() {
        return currentSelection;
    }

    public void setSelection(T selection) {
        if (content.contains(selection)) {
            viewer.setSelection(new StructuredSelection(selection), true);
        }
    }

    public void selectFirstItem() {
        if (content.size()>0) {
            setSelection(content.get(0));
        }
    }
    public void addSelectionListener(TypedComboBoxSelectionListener<T> listener) {
        this.selectionListeners.add(listener);
    }

    public void removeSelectionListener(
            TypedComboBoxSelectionListener<T> listener) {
        this.selectionListeners.remove(listener);
    }

    private T getTypedObject(Object o) {
        if (content.contains(o)) {
            return content.get(content.indexOf(o));
        } else {
            return null;
        }
    }

    private void notifySelectionListeners(T newSelection) {
        for (TypedComboBoxSelectionListener<T> listener : selectionListeners) {
            listener.selectionChanged(this, newSelection);
        }
    }



	public Combo getCombo() {
		return viewer.getCombo();
	}
    
}