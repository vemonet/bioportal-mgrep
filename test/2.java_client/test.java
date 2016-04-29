import edu.umich.med.mbni.mgrepclient.*;
import java.io.*;
import java.net.*;
import java.util.*;

class test1 extends Thread {
	MgrepConnection mCnn;
	String mStr;
	public test1(int i, MgrepConnection cnn, String str) {
		mCnn = cnn;
		mStr = str;
	}
	public void run() {
		try {
			for (int i=0; i<10; i++) {
				Vector<MgrepResult> rtn = mCnn.annotate(true, true, true, mStr);
/*
				for (MgrepResult a : rtn) {
					StringBuffer sb = new StringBuffer();
					sb.append(a.getDictionaryId());
					sb.append('\t');
					sb.append(a.getLocationFrom());
					sb.append('\t');
					sb.append(a.getLocationTo());
					System.out.println(sb);
				}
*/
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
class test2 extends Thread {
	String mStr;
	public test2(String str) {
		mStr = str;
	}
	public void run() {
		try {
			MgrepConnection cnn = new MgrepConnection("localhost:55555");
			for (int i=0; i<100; i++) {
				Vector<MgrepResult> rtn = cnn.annotate(true, true, true, mStr);
/*
				for (MgrepResult a : rtn) {
					StringBuffer sb = new StringBuffer();
					sb.append(a.getDictionaryId());
					sb.append('\t');
					sb.append(a.getLocationFrom());
					sb.append('\t');
					sb.append(a.getLocationTo());
					System.out.println(sb);
				}
*/
			}
			cnn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
public class test {
	public static String URL = "localhost:55555"; //"127.0.0.1:55555,127.0.0.1:55556|127.0.0.1:55557";
	public static int N=50;
	public static void main(String[] args) throws UnknownHostException, IOException, MgrepException, InterruptedException {
		String str = " 123abc€ aBc 123abc€";
		{
			MgrepConnection cnn = new MgrepConnection("localhost:55555");
			Vector<MgrepResult> rtn = cnn.annotate(true, true, true, str);
			for (MgrepResult a : rtn) {
				StringBuffer sb = new StringBuffer();
				sb.append(a.getDictionaryId());
				sb.append('\t');
				sb.append(a.getLocationFrom());
				sb.append('\t');
				sb.append(a.getLocationTo());
				sb.append('\t');
				sb.append(a.getText());
				if (!a.getText().equals(str.substring(a.getLocationFrom()-1, a.getLocationTo())))
					System.exit(1);
				System.out.println(sb);
			}
			cnn.close();
		}
/*
		{
			test2[] thrs = new test2[N];
			for (int i=0; i<N; i++) thrs[i] = new test2(str);
			for (int i=0; i<N; i++) thrs[i].start();
			for (int i=0; i<N; i++) thrs[i].join();
		}
		{
			MgrepConnection cnn = new MgrepConnection("localhost:55555");
			test1[] thrs = new test1[N];
			for (int i=0; i<N; i++) thrs[i] = new test1(i, cnn, str);
			for (int i=0; i<N; i++) thrs[i].start();
			for (int i=0; i<N; i++) thrs[i].join();
			cnn.close();
		}
*/
	}
}
