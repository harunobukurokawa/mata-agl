require recipes-kernel/linux/linux-agl.inc
require recipes-kernel/linux/linux-agl-4.9.inc

FILESEXTRAPATHS_prepend := "${THISDIR}/files:"

#-------------------------------------------------------------------------
#  patches for enabling dcan2 and fix dcan1 & dcan2 adresses

SRC_URI += " \
       file://dcan2_pinmux_enable.patch \
       file://fix_dcan_addresses.patch \
	"

