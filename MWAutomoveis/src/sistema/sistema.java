package sistema;

import controller.controllerTelaSistemaPrincipal;
import java.text.ParseException;

public class sistema {
    public static void main(String[] args) throws ParseException {
        controllerTelaSistemaPrincipal telaPrincipal = new controllerTelaSistemaPrincipal();
        telaPrincipal.executar();
    }
}
