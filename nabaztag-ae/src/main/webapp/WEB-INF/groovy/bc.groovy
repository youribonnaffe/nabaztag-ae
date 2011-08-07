import com.google.appengine.api.utils.SystemProperty

log.info "bootcode"

def bootcode = new File("bootcode.bin").bytes
response.contentLength = bootcode.length
response.setHeader "Server", null
response.status = 200

if (SystemProperty.environment.value() == SystemProperty.environment.value().Production) {
	sout.write bootcode
} else {
	def fin = new File("bootcode.bin").newInputStream()
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
