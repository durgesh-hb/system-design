package design_patterns_2;

public class mian {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		troops troop1 = troopsfactory.crttroopsfact("wizard");
        troops troop2 = troopsfactory.crttroopsfact("archer");
        troops troop3 = troopsfactory.crttroopsfact("barbarian");

        troop1.createtroop();
        troop2.createtroop();
        troop3.createtroop();
		
	}

}
