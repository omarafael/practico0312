package modelo;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;




@Entity
@Table (name="servicio")
@NoArgsConstructor
@Getter @Setter
public class Servicio implements Serializable{
	
	@Id
	@Column	(name="idServicio")
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int idServicio;
	@Column (name="servicio")
	private String servicio;
	@Column (name="tiempoDeResolucion")
	private int tiempoDeResolucion;
	@ManyToMany
	private List <TipoDeProblema> tiposDeProblemas;
	@Column (name="activo")
	private boolean activo = true;
	
	//public Servicio() {}	
	public Servicio( String servicio, int tiempoDeResolucion, 
			boolean activo) {
	
		this.servicio = servicio;
		this.tiempoDeResolucion = tiempoDeResolucion;
		this.activo = activo;
		tiposDeProblemas = new ArrayList<TipoDeProblema>();
	}
	
	public void bajaServicio () {
		this.activo = false;
	}
	
	public void agregarTipoDeProblema (TipoDeProblema tipoDeProblema) {
		tiposDeProblemas.add(tipoDeProblema);
	}
	
	public List tiposDeProblemas () {
		return tiposDeProblemas;
	}
	
	public String getServicio() {
		return this.servicio;
	}
	@Override
	public int hashCode() {
		return Objects.hash(idServicio);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Servicio other = (Servicio) obj;
		return idServicio == other.idServicio;
	}
	
	
	
	
}
