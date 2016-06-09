package by.hrychanok.training.shop.repository.filter;

import java.io.Serializable;

public class Condition implements Serializable{
	public Type type;
	public Comparison comparison;

	/**
	 * value is generally use parameter value
	 */
	public Object value;
	/**
	 * limitValue use for method between
	 */
	public Object limitValue;

	/**
	 * field - name of row in the table
	 */
	public String field;
	public String field2;

	public Condition(Type type, Comparison comparison, Object value, Object limitValue, String field, String field2) {
		super();
		this.limitValue=limitValue;
		this.type = type;
		this.comparison = comparison;
		this.value = value;
		this.field = field;
		this.field2 = field2;
	}

	public Condition() {
		super();
	}

	 public static class Builder {
	        private Type type;
	        private Comparison comparison;
	        private Object value;
	        private Object limitValue;
	        private String field;
	        private String field2;

	        public Builder setType(Type type) {
	            this.type = type;
	            return this;
	        }
	        
			public Builder setComparison(Comparison comparison) {
	            this.comparison = comparison;
	            return this;
	        }

	        public Builder setLimitValue(Object limitValue) {
				this.limitValue = limitValue;
				return this;
			}

			public Builder setValue(Object value) {
	            this.value = value;
	            return this;
	        }

	        public Builder setField(String field) {
	            this.field = field;
	            return this;
	        }
	        public Builder setField2(String field2) {
	            this.field2 = field2;
	            return this;
	        }
	        public Condition build() {
	            return new Condition(type, comparison, value, limitValue, field, field2);
	        }
	    }
}
