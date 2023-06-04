package urna;
// #if Federal || Municipal
public class FederalDeputy extends FederalLegislativeCandidate{
      
    public FederalDeputy(Builder builder) {
        super(builder);
    }

    public static class Builder extends FederalLegislativeCandidate.Builder<Builder>{
        
        @Override
        public Builder getThis(){
            return this;
        }

        public FederalDeputy build() {
            return new FederalDeputy(this);
        }

    }    

};
// #endif
