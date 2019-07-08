/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package demos.client;

import com.sun.istack.internal.logging.Logger;
import demos.db.Product;
import demos.model.ProductManager;
import java.util.List;
import java.util.logging.Level;
import javax.validation.ConstraintViolationException;
import org.eclipse.persistence.exceptions.OptimisticLockException;

/**
 *
 * @author Administrator
 */
public class ProductClient {

	/**
	 * @param args the command line arguments
	 */
	private static final Logger logger
			= Logger.getLogger( ProductClient.class );

	public static void main( String[] args ) {
		try {
			ProductManager pm = new ProductManager( "ProductClientPU" );
			Product p = pm.findProduct( 1 );
			System.out.println( p );
			List<Product> products = pm.findProductByName( "Co%" );
			products.stream().forEach( pr -> System.out.println( pr ) );

			pm.closeEntityManager();

		} catch ( Exception ex ) {
			Throwable cause = ex.getCause();
			if ( cause instanceof ConstraintViolationException ) {
				ConstraintViolationException e
						= ( ConstraintViolationException ) cause;
				e.getConstraintViolations().stream()
						.forEach( v -> logger.log( Level.INFO, v.getMessage() ) );
			} else if ( cause instanceof OptimisticLockException ) {
				OptimisticLockException e
						= ( OptimisticLockException ) cause;
				logger.log( Level.INFO, e.getMessage() );
			} else {
				logger.log( Level.SEVERE, "Product Manager Error", ex );
			}
		}
	}

}
