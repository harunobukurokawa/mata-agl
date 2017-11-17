SUMMARY = "HTTP REST interface to automotive backends for HTML5 UI support"
DESCRIPTION = "Automotive-Framework-Binder Daemon provides a HTTP REST \
interface to various automotive-oriented bindings, \
allowing HTML5 UIs to send platform-specific requests in a secure way."
HOMEPAGE = "https://gerrit.automotivelinux.org/gerrit/#/admin/projects/src/app-framework-binder"

LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://LICENSE-2.0.txt;md5=3b83ef96387f14655fc854ddc3c6bd57"

DEPENDS = "file json-c libmicrohttpd systemd util-linux openssl cynara"

SRC_URI_git = "git://gerrit.automotivelinux.org/gerrit/src/app-framework-binder;protocol=https;branch=${AGL_BRANCH}"
SRC_URI_files = ""
SRC_URI = "${SRC_URI_git} \
           ${SRC_URI_files} \
          "

SRCREV = "f85485a195f304028b6e3a1db4777b59fba692a4"
PV = "master+git${SRCPV}"

S = "${WORKDIR}/git"

inherit cmake pkgconfig

EXTRA_OECMAKE_append_agl-devel = " -DAGL_DEVEL=ON -DINCLUDE_MONITORING=ON"

pkg_postinst_${PN}() {
	mkdir -p "$D${libdir}/afb"
}

#############################################
# setup package
#############################################
PACKAGES += "${PN}-tools ${PN}-meta"

FILES_${PN} += "${datadir}"

FILES_${PN}_append_agl-devel = " ${libdir}/afb/monitoring"

ALLOW_EMPTY_${PN}-meta = "1"

FILES_${PN}-tools = "\
	${bindir}/afb-client-demo \
	${bindir}/afb-genskel \
"

RDEPENDS_${PN}-dev += "libafbwsc-dev"

#############################################
# setup sample binding packages
#############################################
PACKAGES_DYNAMIC = "${PN}-binding-*"

python populate_packages_prepend () {
    afb_libdir = d.expand('${libdir}/afb')
    postinst = d.getVar('binding_postinst', True)
    pkgs = []
    pkgs_dbg = []

    pkgs += do_split_packages(d, afb_libdir, '(.*)-api\.so$', d.expand('${PN}-binding-%s'), 'AFB binding for %s', postinst=postinst, extra_depends=d.expand('${PN}'))
    pkgs += do_split_packages(d, afb_libdir, '(.*(?!-api))\.so$', d.expand('${PN}-binding-%s'), 'AFB binding for %s', postinst=postinst, extra_depends=d.expand('${PN}'))

    pkgs_dbg += do_split_packages(d, oe.path.join(afb_libdir, ".debug"), '(.*)-api\.so$', d.expand('${PN}-binding-%s-dbg'), 'AFB binding for %s, debug info', postinst=postinst, extra_depends=d.expand('${PN}'))
    pkgs_dbg += do_split_packages(d, oe.path.join(afb_libdir, ".debug"), '(.*(?!-api))\.so$', d.expand('${PN}-binding-%s-dbg'), 'AFB binding for %s, debug info', postinst=postinst, extra_depends=d.expand('${PN}'))

    metapkg = d.getVar('PN', True) + '-meta'
    d.setVar('RDEPENDS_' + metapkg, ' '.join(pkgs))
}

#############################################
# setup libafbwsc package
#############################################
PACKAGES =+ "libafbwsc libafbwsc-dev libafbwsc-dbg"

FILES_libafbwsc = "\
	${libdir}/libafbwsc.so.* \
"
FILES_libafbwsc-dev = "\
	${includedir}/afb/afb-wsj1.h \
	${includedir}/afb/afb-ws-client.h \
	${libdir}/libafbwsc.so \
	${libdir}/pkgconfig/libafbwsc.pc \
"
FILES_libafbwsc-dbg = "\
	${libdir}/.debug/libafbwsc.so.* \
	${bindir}/.debug/afb-client-demo \
"
RDEPENDS_libafbwsc-dbg += "${PN}-dbg libafbwsc-dev"


