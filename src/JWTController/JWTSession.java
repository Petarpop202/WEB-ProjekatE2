package JWTController;

import java.security.Key;
import java.util.Date;
import javax.servlet.http.HttpServletRequest;

import beans.Customer;
import beans.User;
import dao.CustomersDAO;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

public class JWTSession {
	
	static Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);

	public void kreiranjeJWT(User korisnik) {
		String jws = Jwts.builder().setSubject(korisnik.getUsername()).setExpiration(new Date(new Date().getTime() + 30 * 6000*10L)).setIssuedAt(new Date()).signWith(key).compact();
		korisnik.setJwt(jws);
	}
	public User proveriJWT(HttpServletRequest zahtev, CustomersDAO korisnikDAO) {
		String auth = zahtev.getHeader("Authorization");
		if ((auth != null) && (auth.contains("Bearer "))) {
			String jwt = auth.substring(auth.indexOf("Bearer ") + 7);
			try {
			    Jws<Claims> claims = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(jwt);
			    String korisnickoIme = claims.getBody().getSubject();
				User korisnik = korisnikDAO.dobaviKorisnika(korisnickoIme);
				if(korisnik == null)
					korisnik = korisnikDAO.adminProfile(korisnickoIme);
				return korisnik;
			} catch (Exception e) {
				return null;
			}
		}
		return null;
	}
}

