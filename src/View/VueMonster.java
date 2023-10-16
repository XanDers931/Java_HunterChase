package View;

import Model.Monster;
import Utils.Observer;
import Utils.Subject;

public class VueMonster implements Observer{

    private Monster monster;

    public void eventMonster(Monster monster){
        //A REMPLIR
    }

    public void creerStage(){
        //A REMPLIR
    }


    @Override
    public void update(Subject subj) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'update'");
    }

    @Override
    public void update(Subject subj, Object data) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'update'");
    }
    
}
