package urna;
// #if Federal || Municipal
import java.util.Set;

public class FederalLegislativeCandidate extends Candidate {

        public String state;

        protected FederalLegislativeCandidate(Builder builder) {
            super(builder);
            this.state = builder.state;
        }

        public static abstract class Builder<T extends Builder<T>> 
                                extends Candidate.Builder<Builder<T>> {

            private String state;

            private Set<String> validStates = Set.of("AC", "AL", "AP", "AM", "BA", "CE", "DF", "ES", "GO", "MA", "MT", "MS", "MG",
            "PA", "PB", "PR", "PE", "PI", "RJ", "RN", "RS", "RO", "RR", "SC", "SP", "SE", "TO");
  
            public T state(String state) {
                verifyValidState(state);this.state = state;
                return this.getThis();
            }

            public FederalLegislativeCandidate build() {
                return new FederalLegislativeCandidate(this);
            }

            protected void verifyValidState(String state){
                
                if (state == null)
                    throw new IllegalArgumentException("state mustn't be null");
        
                if (state.isEmpty())
                    throw new IllegalArgumentException("state mustn't be empty");
                
                if (!this.validStates.contains(state))
                    throw new IllegalArgumentException("state is invalid");
            }
            
            @Override
            public abstract T getThis();

      }

  }

// #endif
