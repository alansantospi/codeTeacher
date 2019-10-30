
public abstract class Veiculo {

		private String cor;
		private String modelo;
		private int peso;
		private int velocidadeMaxima;
		private float preco;
		private float velocidadeAtual;
		private float nivelOleo;

		public Veiculo() {

		}

		public Veiculo(String cor, String modelo, int peso, int velocidadeMaxima, float preco) {

			this.cor = cor;
			this.modelo = modelo;
			this.peso = peso;
			this.velocidadeMaxima = velocidadeMaxima;
			this.preco = preco;
		}

		public float getVelocidadeAtual() {
			return velocidadeAtual;
		}

		public void setVelocidadeAtual(float velocidadeAtual) {
			this.velocidadeAtual = velocidadeAtual;
		}
		
		public int getVelocidadeMaxima() {
			return velocidadeMaxima;
		}

		public void setVelocidadeMaxima(int velocidadeMaxima) {
			this.velocidadeMaxima = velocidadeMaxima;
		}

		public void trocarOleo(float litros) {
			this.nivelOleo = litros;
		}

		public abstract void andar(float distancia);

		public abstract float acelerar(float velocidade);

		public abstract void parar();
}
