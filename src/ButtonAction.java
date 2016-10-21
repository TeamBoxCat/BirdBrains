
public abstract class ButtonAction {
    private Button button;
    
    public ButtonAction(){}
    
    public ButtonAction(Button b){
        this.button = b;
    }
    public abstract void action();
}

