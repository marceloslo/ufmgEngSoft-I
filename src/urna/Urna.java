package urna;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public abstract class Urna {
  
  protected static final BufferedReader scanner = new BufferedReader(new InputStreamReader(System.in));

  protected static boolean exit = false;

  protected static UrnaPrintInterface printInterface = new UrnaPrintInterface();
  
  protected static UrnaBackend urnaModel;
  
  public Urna(String electionPassword) {
	  urnaModel = new UrnaBackend(electionPassword);
  }

  protected static String readString() {
    try {
      return scanner.readLine();
    } catch (Exception e) {
      printInterface.printErrorInReadingEntry(); return readString();
    }
  }

  protected static int readInt() {
    try {
      return Integer.parseInt(readString());
    } catch (Exception e) {
      
      printInterface.printErrorInReadingEntry(); return readInt();
    }
  }
  protected abstract void voterMenu();
  protected abstract void addCandidate(TSEEmployee a);
  protected abstract void removeCandidate(TSEEmployee a);


  public void startMenu() {
    try {
      while (!exit) {
        printInterface.printStartingInterface();
        int command = readInt();
        switch (command) {
          case 1 -> voterMenu();
          case 2 -> tseMenu();
          case 0 -> exit = true;
          default -> printInterface.invalidCommand();
        }
        printInterface.printSeparator();
      }
    } catch (Exception e) {
      printInterface.print(e.getMessage());
    }
  }

  protected static TSEProfessional getTSEProfessional() {
    printInterface.print("Insira seu usuário:");
    String user = readString();
    TSEProfessional tseProfessional = urnaModel.getTseProfessional(user);//TSEMap.get(user);
    if (tseProfessional == null) {
      printInterface.print("Funcionário do TSE não encontrado, por favor confirme se a entrada está correta e tente novamente");
    } else {
      printInterface.print("Insira sua senha:");
      String password = readString();
      // Deveria ser um hash na pratica
      if (tseProfessional.password.equals(password))
        return tseProfessional;
      printInterface.print("Senha inválida, tente novamente");
      printInterface.printSeparator();
    }
    return null;
  }

  protected static void startSession(CertifiedProfessional tseProfessional) {
    try {
      printInterface.askForPassword();
      String pwd = readString();
      urnaModel.startSession(tseProfessional,pwd);//tseProfessional.startSession(currentElection, pwd);
      printInterface.print("Sessão inicializada");
      printInterface.printSeparator();
    } catch (Warning e) {
        printInterface.print((e.getMessage()));
    }
  }

  protected static void endSession(CertifiedProfessional tseProfessional) {
    try {
      printInterface.print("Insira a senha da urna:");
      String pwd = readString();
      urnaModel.endSession(tseProfessional, pwd);//tseProfessional.endSession(currentElection, pwd);
      printInterface.print("Sessão finalizada com sucesso");
      printInterface.printSeparator();
    } catch (Warning e) {
      printInterface.print(e.getMessage());
    }
  }

  protected void showResults(CertifiedProfessional tseProfessional) {
    try {
      printInterface.askForPassword();
      printInterface.print(urnaModel.getFinalResult(tseProfessional, readString())); //tseProfessional.getFinalResult(currentElection, pwd)
      printInterface.printSeparator();
    } catch (Warning e) {
      printInterface.print(e.getMessage());
    }
  }

  protected void tseMenu() {
    try {
      TSEProfessional tseProfessional = getTSEProfessional();
      if (tseProfessional == null)
        return;
      printInterface.printSeparator();
      boolean back = false;
      while (!back) {
        if (tseProfessional instanceof TSEEmployee) {
          printInterface.candidateOptionsMenu();
          int command = readInt();
          switch (command) {
            case 1 -> addCandidate((TSEEmployee) tseProfessional);
            case 2 -> removeCandidate((TSEEmployee) tseProfessional);
            case 0 -> back = true;
            default -> printInterface.invalidCommand();
          }
        } else if (tseProfessional instanceof CertifiedProfessional) {
          printInterface.tseOptionsInUrna();

          int command = readInt();
          switch (command) {
            case 1 -> startSession((CertifiedProfessional) tseProfessional);
            case 2 -> endSession((CertifiedProfessional) tseProfessional);
            case 3 -> showResults((CertifiedProfessional) tseProfessional);
            case 0 -> back = true;
            default -> printInterface.invalidCommand();
          }
        }
      }
    } catch (Warning e) {
      printInterface.print(e.getMessage());
    } catch (Exception e) {
      printInterface.print("Ocorreu um erro inesperado");
    }
  }
}
