package by.hrychanok.training.shop.repository.filter;

public enum Comparison {
	// equal
    eq,
    // greaterThan
    gt,
    // lowerThan
    lt,
    // not equal
    
    /**
	 * Create a predicate for testing the arguments for inequality.
	 */
    ne,
    
    
	/**
	 * Create a predicate to test whether the expression is null.
	 */  
    isnull,

    
    /**
	 * Create predicate to test whether given expression
	 * is contained in a list of values.
	 */
    in,
    
    /**
	 * Create a predicate for testing whether the first argument is
	 * between the second and third arguments in value.
	 */
    between,
    /**
	 * Create a disjunction of the given boolean expressions.
	 *
	 * @param x boolean expression
	 * @param y boolean expression
	 *
	 * @return or predicate
	 */
     or
}
