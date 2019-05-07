// Nom: 


package coffeeshoptp;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ReadOnlyDoubleWrapper;
import javafx.beans.property.ReadOnlyDoubleProperty;
import javafx.beans.binding.DoubleBinding;
import javafx.beans.binding.Bindings;

public class CoffeeShopModel {

	private final double croissantPrixUnitaire = 1.5;
	private final double cafePrixUnitaire = 2;
	private final IntegerProperty croissantProperty = new SimpleIntegerProperty();
	private final IntegerProperty cafeProperty = new SimpleIntegerProperty();
	private final DoubleProperty rabaisProperty = new SimpleDoubleProperty();
	private final ReadOnlyDoubleWrapper montantProperty = new ReadOnlyDoubleWrapper();

	public CoffeeShopModel() {
            
            montantProperty.bind(calculeMontant());
            
		//cafeProperty.addListener((prop, oldVal, newVal) -> { calculerMontant(); });
		//croissantProperty.addListener((prop, oldVal, newVal) -> { calculerMontant(); });
//                montantProperty.bind(cafeProperty()
//                        .multiply(cafePrixUnitaire)
//                        .add(croissantProperty()
//                                .multiply(croissantPrixUnitaire)));

                
		
	}
        
        private final DoubleBinding calculeMontant(){
            DoubleBinding dbBind = new DoubleBinding() {
                {
                    super.bind(cafeProperty(),croissantProperty(),rabaisProperty());
                }
                @Override
                protected double computeValue() {
                    double fullprice = cafeProperty().get() * cafePrixUnitaire
                            + croissantProperty().get() * croissantPrixUnitaire;
                    
                    double rabais = (fullprice * rabaisProperty.get())/100;
                    
                    return (fullprice-rabais);
                }
            };
            return dbBind;
        }

	public final double getRabais() {
		return (double) rabaisProperty.get();
	}

	public final void setRabais(double rabais) {
		rabaisProperty.set(rabais/100);
		
	}
	
	public final DoubleProperty rabaisProperty() {
		return rabaisProperty;
	}

	public final int getCroissants() {
		return croissantProperty.get();
	}
	
	public final IntegerProperty croissantProperty() {
		return croissantProperty;
	}

	public final void setCroissants(int nombre) {
		cafeProperty.set(nombre);
	}

	public final int getCafes() {
		return cafeProperty.get();
	}

	public final void setCafes(int nombre) {
		cafeProperty.set(nombre);
	}

	public final IntegerProperty cafeProperty() {
		return cafeProperty;
	}

	public final ReadOnlyDoubleProperty montantProperty() {
		return montantProperty.getReadOnlyProperty();
	}

	private void calculerMontant() {
		montantProperty.set(getCafes() * cafePrixUnitaire + 
				getCroissants() * croissantPrixUnitaire);
	}

}
