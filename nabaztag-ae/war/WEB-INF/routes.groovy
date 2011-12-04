get "/vl/bc.jsp", forward: "/vl/bc.groovy"
get "/vl/locate.jsp", forward: "/vl/locate.groovy"
get "/vl/p4.jsp", forward: "/vl/ping.groovy"
get "/vl/rfid.jsp", forward: "/vl/rfid.groovy"

get "/cron", forward: "/vl/cron.groovy"
get "/chor", forward: "/vl/chor.groovy"

get "/debug", forward: "/debug.groovy"

get "/web", forward: "/web/home.groovy"
get "/web/lastRfid", forward: "/web/lastRfid.groovy"

get "/web/editRabbit", forward: "/web/editRabbit.groovy"
get "/web/editRfid", forward: "/web/editRfid.groovy"

post "/web/saveRabbit", forward: "/web/saveRabbit.groovy"
post "/web/saveRfid", forward: "/web/saveRfid.groovy"

post "/web/register", forward: "/web/register.groovy"

get "/", redirect: "/web"
