package vl
import com.google.appengine.api.utils.SystemProperty

log.info "bootcode"

def bootcode = new File("bootcode.bin")
def bootcodeAsBytes = bootcode.bytes
response.contentLength = bootcodeAsBytes.length

if (app.env.name == SystemProperty.Environment.Value.Production) {
	sout.write bootcodeAsBytes
} else {
	sendBootCodeSlowly(bootcode)
}
response.status = 200


private sendBootCodeSlowly(bootcode) {
	def fin = bootcode.newInputStream()
	while (true) {
		int available = fin.available();
		if (available > 0) {
			int bufferSize = available;
			int BUFFER_SIZE = 1000;
			if (available > BUFFER_SIZE)
				bufferSize = BUFFER_SIZE;
			byte[] buffer = new byte[bufferSize];
			fin.read(buffer);
			sout.write(buffer);
			sout.flush();
			try {
				Thread.sleep(50);
			} catch (InterruptedException e) {
				// Ignoring this exception
			}
			continue;
		}
		break;
	}
}
