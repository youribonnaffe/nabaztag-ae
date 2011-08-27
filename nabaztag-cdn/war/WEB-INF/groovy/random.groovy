class Rand {
	static def random = new Random()
}

redirect "/surprise/"+(Rand.random.nextInt(250)+1)+".mp3"