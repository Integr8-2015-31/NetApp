


import java.awt.*;
import java.awt.image.*;
import javax.swing.JFrame;

public class CanvasDisplay extends Canvas{

	private static final long serialVersionUID = 1L;
	private JFrame frame;
	
	private BufferedImage I;
	private ShapeDrawer P;
	private byte[] parts;
	private BufferStrategy bufferstrat;
	private Graphics G;
	
	
	public CanvasDisplay(int width, int height, String title,byte Background) {
		Dimension size = new Dimension(width, height);	
		setPreferredSize(size);
		setMinimumSize(size);
		setMaximumSize(size);
		
		P = new ShapeDrawer(width, height,Background);
		I= new BufferedImage(width, height, BufferedImage.TYPE_3BYTE_BGR);
		parts = 
		((DataBufferByte)(I.getRaster().getDataBuffer())).getData();
		
		P.clear((byte)(0x00));
		frame = new JFrame();
		frame.add(this);
		frame.pack();
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		frame.setTitle(title);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		
		createBufferStrategy(1);
		bufferstrat = getBufferStrategy();
		G = bufferstrat.getDrawGraphics();
	}
	
	public void SwapBuffers() {
		P.CopyToByteArray(parts);
		G.drawImage(I, 0, 0, P.GetWidth(), P.GetHeight(), null);
		bufferstrat.show();
	}
	
	public ShapeDrawer GetBitmap() { 
		return P;
		}
	
	public JFrame getFrame() {
		return frame;
	}
	
	public void dispose() {
		frame.dispose();
	}
}	

	

