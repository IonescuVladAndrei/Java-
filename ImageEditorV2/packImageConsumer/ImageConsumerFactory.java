package packImageConsumer;

import java.io.DataOutputStream;

import packWork.ImageBuffer;
import packWork.ImagePath;

public class ImageConsumerFactory {
	public ImageConsumer createImageConsumer(int filter, ImageBuffer b, int position, ImagePath inputPath, int filterValue, DataOutputStream out, ImageBuffer bTemp) {
		if(b == null || out == null) {
			System.out.println("ImageConsumerFactory: ImageBuffer este null sau DataOutputStream este null.");
			return null;
		}
		switch(filter) {
			case 1:
				return new ImageConsumerNegative(b, position, inputPath, filterValue, out);
			case 2:
				return new ImageConsumerGreyVerOne(b, position, inputPath, filterValue, out);
			case 3:
				return new ImageConsumerGreyVerTwo(b, position, inputPath, filterValue, out);
			case 4:
				return new ImageConsumerBright(b, position, inputPath, filterValue, out);
			case 5:
				return new ImageConsumerSat(b, position, inputPath, filterValue, out);
			case 6:
				return new ImageConsumerGama(b, position, inputPath, filterValue, out);
			case 7:
				return new ImageConsumerSepia(b, position, inputPath, filterValue, out);
			case 8:
				return new ImageConsumerContrast(b, position, inputPath, filterValue, out);
			case 9:
				return new ImageConsumerMirrorBY(b, position, inputPath, filterValue, out, bTemp);
			case 10:
				return new ImageConsumerPaint(b, position, inputPath, filterValue, out , bTemp);
			case 11:
				return new ImageConsumerRedFilter(b, position, inputPath, filterValue, out);
			case 12:
				return new ImageConsumerGreenFilter(b, position, inputPath, filterValue, out);
			case 13:
				return new ImageConsumerBlueFilter(b, position, inputPath, filterValue, out);
			default:
				throw new IllegalArgumentException("ImageConsumerFactory: filtrul cu valoarea " + filter + " nu a fost adaugat inca.");
		}
	}
}
