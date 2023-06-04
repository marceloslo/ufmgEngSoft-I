package urna;
// #if Federal || Municipal

public class StateDeputy extends FederalLegislativeCandidate{
      
    public StateDeputy(Builder builder) {
        super(builder);
    }

    public static class Builder extends FederalLegislativeCandidate.Builder<Builder>{
        
        @Override
        public Builder getThis(){
            return this;
        }

        public StateDeputy build() {
            return new StateDeputy(this);
        }

    }    

};
// #endif
