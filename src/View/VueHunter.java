package View;

import Model.Hunter;
import Model.Monster;
import Utils.Observer;
import Utils.Subject;

public class VueHunter implements Observer{

    private Hunter hunter;

    public void eventHunter(Hunter hunter){
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
