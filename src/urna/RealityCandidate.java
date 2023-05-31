package urna;

public class RealityCandidate extends Candidate {

        public String nationality;

        protected RealityCandidate(Builder builder) {
            super(builder);
            this.nationality = builder.nationality;
        }

        public static abstract class Builder<T extends Builder<T>> 
                                extends Candidate.Builder<Builder<T>> {

            private String nationality;
  
            public T nationality(String nationality) {
                verifyValidNationality(nationality);this.nationality = nationality;
                return this.getThis();
            }

            public RealityCandidate build() {
                return new RealityCandidate(this);
            }

            protected void verifyValidNationality(String district){
                
                if (nationality == null)
                    throw new IllegalArgumentException("nationality mustn't be null");
        
                if (nationality.isEmpty())
                    throw new IllegalArgumentException("nationality mustn't be empty");
            }
            
            @Override
            public abstract T getThis();

      }

}
