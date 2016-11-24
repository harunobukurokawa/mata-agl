FILESEXTRAPATHS_prepend := "${THISDIR}/linux:"

# Enable support for TP-Link TL-W722N USB Wifi adapter and RTL2832U DVB USB
# adapter.
SRC_URI += "file://ath9k_htc.cfg \
            file://rtl_sdr.cfg "
KERNEL_CONFIG_FRAGMENTS_append = " ${WORKDIR}/ath9k_htc.cfg ${WORKDIR}/rtl_sdr.cfg"