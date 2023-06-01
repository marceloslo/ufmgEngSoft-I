package urna;

public class UrnaPrintInterface {
  
    public void print(String output) {
        System.out.println(output);
    }

    public void printErrorInReadingEntry(){
        print("\nErro na leitura de entrada, digite novamente");
    }

    public void printStartingInterface(){
        print("Escolha uma opção:\n");
        print("(1) Entrar (Eleitor)");
        print("(2) Entrar (TSE)");
        print("(0) Fechar aplicação");
    }

    public void printSeparator(){
        print("\n=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=\n\n");
    }
    public void candidateNotFound(){
        print("Nenhum candidato encontrado com este número, tente novamente");
        this.printSeparator();
    }

    public void tseOptionsInUrna(){
        
        print("(1) Iniciar sessão");
        print("(2) Finalizar sessão");
        print("(3) Mostrar resultados");
        print("(0) Sair");
    }

    public void federalElectionsOptionsToVote(){
        this.printSeparator();
        print("Qual a categoria de seu candidato?\n");
        print("(1) Presidente");
        print("(2) Deputado Federal");
        print("(3) Deputado Estadual");
        print("(4) Senador");
        print("(5) Governador");
    }

    public void districtalElectionsOptionsToVote(){
        this.printSeparator();
        print("Qual a categoria de seu candidato?\n");
        print("(1) Prefeito");
        print("(2) Vereador");
    }

    public void TvShowElectionsOptionsToVote(){
        this.printSeparator();
        print("Qual a categoria de seu candidato?\n");
        print("(1) Participante Reality");
    }

    public void invalidCommand(){
        print("Comando inválido\n");
    }

    public void publicConfirmOrChange(){
        print("(1) Confirmar\n(2) Mudar voto");
    }


    public void confirmationMessage(String type){
      print("Você está votando " + type + "\n");
      this.publicConfirmOrChange();
    }


    public void confirmationCandidate(String name, String party){
        print(name + " do " + party + "\n");
        this.publicConfirmOrChange();
    }

    public void candidateOptionsMenu(){
        print("(1) Cadastrar candidato");
        print("(2) Remover candidato");
        print("(0) Sair");
    }
    public void askForPassword(){
        print("Insira a senha da Urna.");
    }
    public void askForCandidateNumber(String type){
        print("(ext) Desistir");
        print("Digite o número do candidato escolhido por você para " + type);
    }
    public void displayYesOrNo(){
        print("(1) Sim\n(2) Não");
    }

    public void printTypeOfVotingMachine(){

        print("Qual o tipo de máquina de votação?\n");
        print("(1) Urna Federal");
        print("(2) Urna Distrital");
        print("(3) Urna TV Show");
        print("(4) Urna Universidade");

    }

    public void printInvalidVotingMachine(){
        print("Número passado para escolha de máquina de votação é inválido");
    }


}