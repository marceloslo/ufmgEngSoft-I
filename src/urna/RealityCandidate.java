package urna;
// #if RealityShow
//@
//@public class RealityCandidate extends Candidate {
//@
//@        public String nationality;
//@
//@        protected RealityCandidate(Builder builder) {
//@            super(builder);
//@            this.nationality = builder.nationality;
//@        }
//@
//@        public static class Builder extends Candidate.Builder <Builder> {
//@
//@            private String nationality;
//@  
//@            public Builder nationality(String nationality) {
//@                verifyValidNationality(nationality);
//@                this.nationality = nationality;
//@                return this;
//@            }
//@
//@            public RealityCandidate build() {
//@                return new RealityCandidate(this);
//@            }
//@
//@            protected void verifyValidNationality(String nationality){
//@                
//@                if (nationality == null)
//@                    throw new IllegalArgumentException("nationality mustn't be null");
//@        
//@                if (nationality.isEmpty())
//@                    throw new IllegalArgumentException("nationality mustn't be empty");
//@            }
//@
//@          @Override
//@          public Builder getThis() {
//@              return this;
//@          }
//@
//@      }
//@
//@}
// #endif
