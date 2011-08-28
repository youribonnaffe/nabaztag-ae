get "/vl/bc.jsp",  		forward: "/bc.groovy"
get "/vl/locate.jsp",	forward: "/locate.groovy"
get "/vl/p4.jsp",		forward: "/ping.groovy"
get "/vl/rfid.jsp",		forward: "/rfid.groovy"

get "/web", forward: "/home.groovy"
get "/web/lastRfid", forward: "/lastRfid.groovy"
post "/web/saveRfid", forward: "/saveRfid.groovy"

get "/web/register", forward: "/register.groovy"
post "/web/register", forward: "/register.groovy"
get "/", redirect: "/web"