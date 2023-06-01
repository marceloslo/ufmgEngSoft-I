package urna;

public class Candidate {

    protected String name;
    protected String party;
    protected int number;
    public int numVotes;

      protected Candidate(Builder<?> builder) {
          this.name = builder.name; this.party= builder.party;
          this.number = builder.number; this.numVotes = 0;
      }

      public static Builder builder() {
          return new Builder() {
              @Override
              public Builder getThis() {
                  return this;
              }
          };
      }

      public abstract static class Builder<T extends Builder<T>> {

          protected String name;
          protected String party;
          protected int number;

          public abstract T getThis();

          public T name(String name) {
              this.verifyStringConditions(name); this.name = name;
              return this.getThis();
          }

          public T party(String party) {
              this.verifyStringConditions(party); this.party = party;
              return this.getThis();
          }
          
          public T number(int number) {
              this.verifyNumberConditions(number); this.number = number;
              return this.getThis();
          }
          
          private void verifyNumberConditions(int number){
            if (number <= 0)
              throw new IllegalArgumentException("number mustn't be less than or equal to 0");
    
          }

          private void verifyStringConditions(String name){

            if (name == null)
              throw new IllegalArgumentException("String passed to builder mustn't be null");

            if (name.isEmpty())
              throw new IllegalArgumentException("String passed to builder mustn't be empty");

          }

          public Candidate build() {
              return new Candidate(this);
          }
      }

  }
