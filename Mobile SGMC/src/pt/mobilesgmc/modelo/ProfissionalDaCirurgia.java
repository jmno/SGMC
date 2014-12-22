package pt.mobilesgmc.modelo;

public class ProfissionalDaCirurgia {

	private ProfissonalSaude profissional;
	private String tipo;
	
	public ProfissionalDaCirurgia()
	{
	}

	public ProfissonalSaude getProfissional() {
		return profissional;
	}

	public void setProfissional(ProfissonalSaude profissional) {
		this.profissional = profissional;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	
	public ProfissionalDaCirurgia(ProfissonalSaude p, String tipo)
	{
		this.profissional = p;  
		this.tipo = tipo;
	}
	
	@Override
	public String toString() {
		return profissional.toString() + " " + tipo ;
	}
}
