create database ShoppingWebDB;
go
use ShoppingWebDB;
go
create table Categories (
	categoryID int not null primary key identity,
	categoryName varchar(30) not null
);
go

create table Manufacture (
	manufactureID int not null primary key identity,
	manufactureName varchar(30) not null
);
go

create table Promotions (
	promotionID int not null primary key,
	promotionName nvarchar(255) not null,
	promotionDescription nvarchar(1024) not null,
	startDay date not null,
	endDay date not null,
	pictureLink varchar(255),
	promotionDiscount int
);
go

create table Products (
	productID int not null primary key,
	categoryID int not null references Categories(categoryID),
	manufactureID int not null references Manufacture(manufactureID),
	productName varchar(255) not null,
	productDescription nvarchar(1024) not null,
	productPrice money not null,
	modelYear int not null,
	quantityInStore int not null,
	productWarranty int not null,
	promotionID int references Promotions(promotionID) default 0,
	pictureLink varchar(255)
);
go

create table Customers (
	customerID int not null identity primary key,
	customerName nvarchar(100) not null,
	customerEmail varchar(50) not null,
	customerPassword varchar(50) not null,
	customerAddress nvarchar(255) not null,
	customerPhone varchar(50) not null,
	customerNote nvarchar(255),
	isActive bit default 0,
	avatarLink varchar(1024) default '/images/avatar_profile/avatar.png'
);
go
create table Invoices (
	invoiceID int not null identity primary key,
	customerID int not null references Customers(customerID),
	invoiceDate date not null,
	invoiceTotal money not null,
	shippingProcess char(20) not null,
	shippingDate date,
	isPaid bit not null
);
go
create table LineItem (
	lineItemID int not null identity primary key,
	invoiceID int not null references Invoices(invoiceID),
	productID int not null references Products(productID),
	quantity int not null,
	lineItemAmount money not null
);
go
create table Users (
	userID int not null identity primary key,
	userName varchar(50) not null,
	userPass varchar(50) not null,
	userRole varchar(50) not null,
	isActive bit default 0
);
go
create table EmailKey (
	keyID int not null,
	customerEmail varchar(100) not null
);
go
insert into Users values
('admin','admin','administrator',1);
go
insert into Categories(categoryName) values
('Laptop'),
('Mobile'),
('Tablet'),
('Desktop');
go
insert into Manufacture(manufactureName) values
('Acer'),
('HP'),
('Asus'),
('Lenovo'),
('Samsung'),
('Nokia'),
('LG'),
('Novo');
go
insert into Promotions values
(12015,N'Discount 50%',N'Happy Hour Sale — All Orders Between 5pm — 7pm 50% Off','2015/06/01','2015/12/31','/images/012015.jpg',50),
(22015,N'30% OFF on all Drupal products this Easter',N'Celebrate a happy Easter with chicken, cake, face painting, toys, balloons, bubbles, your loved ones and also our Easter eggs! From 2 to 6 April, ThemeBrain’s Easter Promotion will bring you all Drupal products at an Easter price with 30% OFF','2015/08/01','2015/12/31','/images/022015.jpg',30);
go
insert into Promotions values
(0,N'No Promotion',N'No Promotion','','','',0);
go

insert into Products
values 
(40814,1,1,'NOTEBOOK ACER ONE 14-Z1401(CELERON N2840/ 4G/ 500G/ 14/ DOS',N'Intel Celeron N2840 (2*2.16 GHz, 2MB Cache, 1600Mhz FSB , 22nm, 7.5W)| Intel 8 Series Express| 2GB DDR3L BUS 1600Mhz, 500GB SATA| 14 inch, HD (1366 x 768 pixels)| Intel HD GPU Graphics| Wireless 802.11b/g/n Half Mini Card| Card Reader 5.1, Webcam HD, Microphone| Bluetooth, HDMI, USB 3.0| 2.1 kg, DOS, Màu đen, màu trắng,',4990000.00,2013,20,12,12015,'/images/40814.jpg'),
(39252,1,2,'NOTEBOOK HP STREAM 13 -C001TU(CELERON N2840/ 2GR3/ 32G/ 13.3/ W8.1)',N'Intel Celeron N2840 (2*2.16 GHz, 2MB Cache, 22nm, 7.5W)| Intel 8 Series Express| 2GB DDR3L BUS 1600Mhz, 32GB| 13.3 LED HD (1366 x 768)| Intel HD GPU Graphics| Wireless RTL 8723BE 802.11 b/g/n, NIC 10/100 Mbps| Card Reader 5.1, Webcam 1.3M, Microphone| Bluetooth 4.0, HDMI, USB 3.0| 1.7kg, Pin 2 Cell, Win 8.1 BING| Màu: Xanh, Free Office 365 1 year| Free 1Tb Onedrive 1 year',4990000.00,2013,10,12,22015,'/images/39252.jpg'),
(43035,1,3,'NOTEBOOK ASUS E402MA - WX0038D(CELERON N2840/ 2G/ 500G/ 14/ DVDRW/ DOS)',N'Intel Celeron N2840 (2*2.16 GHz, 2MB Cache, 22nm, 7.5W)| Intel 8 Series Express| 2GB DDR3 BUS 1600Mhz, 500 GB SATA| 14.0 LED HD (1366 x 768) 16:9 Gloss| Intel HD GPU Graphics| Wireless 802.11 b/g/n, NIC 1Gbps| Card Reader 5.1, Webcam, Microphone| HDMI, VGA Port, 2.1Kg| Pin 2 Cell, DOS, Pin & Adapter| bảo hành 12 tháng, Màu : Xanh Đen,',5090000.00,2013,15,12,12015,'/images/43035.jpg'),
(42194,1,4,'NOTEBOOK LENOVO 15 100- 80MJ0032VN(N2840/ 4G/ 500G/ 15.6/ DOS / 4CELL)',N'Intel Celeron N2840 (2*2.16 GHz, 2MB Cache, 22nm, 7.5W)| Intel 8 Series Express| 4GB DDR3L BUS 1600Mhz, 500GB SATA DVD-RW| 15.6 LED HD (1366 x 768) 16:9 Gloss| Intel HD GPU Graphics 4000| Wireless 802.11 b/g/n| Card Reader 5.1 Webcam 1.3M, Microphone| Bluetooth 4.0, HDMI, USB 3.0| 2.1kg, Pin 4 Cell, DOS| Màu : Đen, Model mới, thiết kế mỏng đẹp| Đã up Ram lên 4GB', 5590000.00,2015,30,12,22015,'/images/42194.jpg'),
(43136,1,3,'NOTEBOOK ASUS E502MA - XX0004D(CELERON N2840/ 2G/ 500G/ 15/ DOS)',N'Intel Celeron N2840 (2*2.16 GHz, 2MB Cache, 22nm, 7.5W)| Intel 8 Series Express| 2GB DDR3 BUS 1600Mhz, 500 GB SATA| 15.6 LED HD (1366 x 768) 16:9 Gloss| Intel HD GPU Graphics| Wireless 802.11 b/g/n, NIC 1Gbps| Card Reader 5.1, Webcam| Microphone, HDMI, VGA Port| 2.3Kg, Pin 2 Cell, DOS, Pin & Adapter| bảo hành 12 tháng, Màu : Xanh Đen,',5350000.00,2014,5,24,0,'/images/43136.jpg');
go
insert into Products
values 
(41401,2,5,'MOBILE SAMSUNG GALAXY V PLUS - G318 (WHITE)',N'Dual-core 1.2 GHz| 4.0 inch, 480 x 854 pixels. WVGA| Android 4.4, RAM 512MB, ROM 4GB| hỗ trợ thẻ nhớ lên đến 32 GB| Chính: 3.0 MP, Phụ: VGA| Wi-Fi 802.11 b/g/n, Wi-Fi Direct| A-GPS, GLONASS, 3GP, MP4, AVI, H.263, H.264(MPEG4-AVC)| 1500 mAh, 2G GSM 850/900/1800/1900, 3G HSDPA 900/2100| MP3, WAV, AAC, AAC+, AMR| 121.2 x 62.7 x 10.65 mm, 123 g, 2 sim 2 sóng',1690000.00,2013,25,12,12015,'/images/41401.jpg'),
(35565,2,6,'MOBILE NOKIA 530 ( WHITE)',N'Qualcomm Snapdragon 200, Quad-core 1.2 GHz| GPU :Adreno 302, 4.0 inch (480 x 854 pixels)| TFT 16 triệu màu, FWVGA| Windows Phone 8.1 4GB| hỗ trợ thẻ nhớ lên đến 32GB| Chính 5.0 MP , lấy nét cố định| Wifi , 3G , GPS : A-GPS & GLONASS| GPRS , EDGE| Pin chuẩn Lithium-ion, 1430 mAh, 9h /230h| Wi-Fi 802.11 b/g/n, Wi-Fi hotspot |3G : HSDPA 900/ 1200 MHz| Rung , nhạc chuông Mp3| 119.7 x 62.3 x 11.7 mm, 129 g| 2 Sim 2 sóng |Quay phim : WVGA@30fps, Xem phim , nghe nhạc | Ghi âm , FM , ... |Hàng chính hãng NOKIA',1790000.00,2014,16,12,12015,'/images/35565.jpg'),
(35566,2,6,'MOBILE NOKIA 530 ( ORANGE)',N'Qualcomm Snapdragon 200, Quad-core 1.2 GHz| GPU :Adreno 302, 4.0 inch (480 x 854 pixels)| TFT 16 triệu màu, FWVGA| Windows Phone 8.1, 4GB| hỗ trợ thẻ nhớ đến 32 GB| Chính 5.0 MP , lấy nét cố định| Wifi , 3G , GPS : A-GPS & GLONASS| GPRS , EDGE|Pin chuẩn Lithium-ion, 1430 mAh, 9h / 230h| Wi-Fi 802.11 b/g/n, Wi-Fi hotspot| 3G : HSDPA 900/ 1200 MHz| Rung , nhạc chuông Mp3| 119.7 x 62.3 x 11.7 mm, 129g| 2 Sim 2 sóng |Quay phim : WVGA@30fps, Xem phim , nghe nhạc| Ghi âm , FM , ...| Hàng chính hãng NOKIA',1790000.00,2014,21,12,22015,'/images/35566.jpg'),
(32314,2,6,'MOBILE NOKIA LUMIA 525 ( ORANGE)',N'1 GHz 2 nhân, Chipset :Qualcomm Snapdragon S4| GPU:Adreno 305, IPS LCD 16 triệu màu, 4.0 inch| Windows Phone 8, 8 GB Ram 1 GB| hỗ trợ thẻ nhớ 64 GB| 5.0 MP, Tự Động lấy nét , quay phim| Wi-Fi 802.11 b/g/n, Wi-Fi hotspot,GPRS, Bluetooth,3G,EDGE| Nghe nhạc , xem phim , mạng xã hội ,xem tài liệu văn phòng| Pin Nokia BL-5J 1430 mAh| 3G :HSDPA, 21 Mbps; HSUPA, 5.76 Mbps, có| 119.9 x 64 x 9.9 mm, 124g| Mạng xã hội ảo , You Tube, Gmail | Micro chuyên dụng chống ồn| Hàng chính hãng NOKIA',1790000.00,2015,33,12,0,'/images/32314.jpg'),
(33582,2,7,'MOBILE LG E455 (BLACK)',N'MTK 6575 Core 1Ghz| WVGA, 4.0 , 480 x 800 pixels, IPS LCD, 16 triệu màu| Android 4.1.2 (Jelly Bean), Ram 512 MB, Rom 4GB| hỗ trợ lên đến 32GB| 5.0 MP (2592 x 1944 pixels), Quay phim VGA@30fps| 3G/USB/Wifi/Bluetooth/EDGE/GPRS 2 sim 2 sóng| FM, Games, Nghe nhạc và xem phim đa định dạng| Pin chuẩn Li-Ion, 1700 mAh| 2G GSM 850/900/1800/1900, 3G HSDPA 850/900/1900/2100| Báo rung, Chuông mp3, Chuông wav| 117.5 x 62.2 x 9.2 mm, 103.3,',1790000.00,2014,12,12,0,'/images/33582.jpg');
go
insert into Products
values
(34802,3,8,'MTB NOVO 7 NUMY 3G - RAINBOW II (WHITE)',N'Dual core 1.5Ghz, Allwinner A20| 512 MB, 8G ROM| inches ( 800x480 pixel (WVGA)| cảm ứng đa điểm|hỗ trợ Wifi chuẩn b/g, kết nối 3G + Bluetooth| Bluetooth 4.0, GPS, 1.2M Video Camera(front)| Hỗ trợ USB 3G Dongle| -0.3MP Camera truoc + 5MP camera sau| 265g, Android Ice Cream Sandwich 4.0 (4.0.3)| Pin :2400mAh, Hàng chính hãng NOVO',990000.00,2013,21,12,22015,'/images/34802.jpg'),
(34806,3,8,'MTB NOVO 8 ADVANCED MINI (WHITE)',N'Dual - Core, 1.3 GHz | GPU :PowerVR SGX540, ATM7021, 512 MB| ROM : 8GB, TFT, 7.85 inch| Wifi chuẩn 802.11 b/g/n, Micro HDMI| hỗ trợ kết nối USB 3G thông qua cổng OTG| Trước : 1MP(1280x720 pixels), Sau 5 MP(2592 x 1944 pixels)| 136.1x200.4x10mm , 382 g, Android 4.2| Dung lượng Pin :3000mAh , Lithium - Ion| Quay phim : HD 720p(1280x720 pixels), quay phim Panorama | hỗ trợ Word, Excel, PPT,PDF, MSN| Hàng chính hãng NOVO',1190000.00,2014,6,12,12015,'/images/34806.jpg'),
(39842,3,4,'MTB LENOVO TAB2-A7-10',N'Quad-core, 1.3 GHz, MediaTek MT 8127| 1 GB, 8 GB, Hỗ trợ thẻ nhớ ngoài đến 32GB| IPS-LCD, 7 inch, Mali-450MP| Không 3G, Wifi chuẩn 802.11 b/g/n| Đàm thoại : Không| Camera truoc : 0.3 MP(VGA 640 x 480 pixels)| 269g, Android 4.4| Dung lượng pin 3450mAh,',1690000.00,2013,17,12,0,'/images/39842.jpg'),
(37583,3,1,'MTB ACER ICONIA ONE 7 A1-713 - 8GB - L7ASC001 (BLACK)',N'Quad-core, 1.3 GHz, :MediaTek MTK 8382| 1 GB, ROM : 08GB, hỗ trợ thẻ nhớ ngoài lên đến 32GB| TFT, 7 inch , 1024 x 600 pixels) , 16 triệu màu,| 3G( Download 21 Mbps, Upload 5.76 Mbps), Wifi chuẩn 802.11 b/g/n| Micro USB, bluetooth 4.0, jack 3.5mm| Camera :2 MP(1600 x 1200 pixels) , trước :0.3 MP(VGA 640 x 480 pixels)| 298 g, pin 3400mAh| Android 4.4.2 Wifi | 3G, SIM thường, Đàm thoại, Micro Sim| Quay phim , Nghe nhạc , Ghi âm , FM ...| Văn phòng : hỗ trợ Word, Excel, PPT,PDF, MSN...| Hàng chính hãng ACER',1990000.00,2015,16,12,0,'/images/37583.jpg'),
(37807,3,4,'MTB LENOVO A3300 -59425674(MT8382/ 1G/ 8G/ 7.0/ 3G/ CALL/ AD/ 3G)',N'Quadcore 1.3GHz, ARM ARMTM Cortex A7 MT8382| 1 GB, 08 GB, micro SD hỗ trợ tối đa 32 GB| 7 inch (600 x 1024 )pixel 16 triệu màu, cảm ứng điện dung| Bluetooth 4.0, GPS,WIFI,3G, Đàm thoại| WIFI 802.11 b/g/n, Bluetooth 4.0| Sau 2 MP(1600 x 1200 pixels), trước 0.3 VGA| 198 x 119.8 x 10.5 mm, Android 4.4.2| Pin Li-ion 3500 mAh,',2290000.00,2014,13,12,12015,'/images/37807.jpg');
go
insert into Products
values
(40250,4,3,'PC ASUS K30AD-VN018D (G3240/ 2G/ 500G/ RW/ READER/ WIFI/ BT4.0/ K/M)',N'CPU Intel Pentium Dual Core G3240 (2x3.3Ghz, 3MB L2, GPU)| Intel H81 Express, 2GB DDR3 1600MHz (2 slot, Max 16GB)| 500GB SATA 7200rpm, DVD-RW| Integrated Graphics; 1 x D-Sub (VGA)| 1 x HDMI; 7.1 channel Audio| 10/100/1000Mbps; 802.11 a/b/g/n/ac| Bluetooth V4.0, 4 in1 Card Reader + 2 x USB 3.0 (front)| 4 x USB 2.0 (rear), Free DOS, 250 Watt PSU. 8.7kg| USB ASUS Keyboard & Mouse Case Lớn',6650000.00,2015,9,36,0,'/images/40250.jpg'),
(40877,4,1,'PC ACER AS TC705 (DT.SXNSV.001) (G3250/ 2GB/ 500G/ DVD-RW/ KEY/ MOUSE)',N'CPU INTEL Pentium Dual Core G3250 (2x3.3Ghz, 3MB L2, GPU) 1150 HASWELL| Intel H81 Express, DDR3 2GB 1600Mhz| Serial ATA (SATA) 500GB - 7200 rpm, SuperMulti DVD+/-RW| Intel GMA HD Integrated graphics| Gigabit Ethernet 10 /100 /1000 Mbps| 2xUSB3.0 front; 4xUSB 2.0 back;| Card Reader 6 in 1, DOS, USB Keyboard & optical mouse| Case form Micro Tower',7650000.00,2015,14,36,12015,'/images/40877.jpg'),
(35323,4,1,'PC ACER AS XC605 (DT.SRPSV.016) (G3240/ 2G/ 1TB/ DVDRW/ READER/ K/M/ DOS)',N'CPU Intel Pentium Dual Core G3240 (2x3.2Ghz, 3MB L2, GPU) LGA1150| Intel H81 Express, DDR3 2GB 1600Mhz| 1TB SATA 7200rpm, SuperMulti DVD+/-RW| Intel GMA HD Integrated graphics| Gigabit Ethernet 10 /100 /1000 Mbps| 2xUSB3.0 front; 4xUSB 2.0 back| Card Reader 6 in 1, DOS, USB Keyboard & optical mouse| Case mini slim',4650000.00,2014,21,36,22015,'/images/35323.jpg'),
(42682,4,4,'PC LENOVO H50-50 (90B700D3VN) (G3260/ 2G/ 500G/ RW/ 7IN1/ WIFI/ K/M/ DOS)',N'CPU Intel Pentium Dual Core G3260 (2x3.4Ghz, 3MB L2, GPU) LGA1150| Intel H81 Chipset, DDR3 2GB/1600Mhz (2 slot)| 500GB SATA 7200rpm, DVD-RW| Intel HD Integrated Graphics, 10/100/1000Mbps| Wireless 802.11bgn, 1xHDMI, 1xVGA| 4xUSB 2.0 (2 front), 2xUSB 3.0| Card reader 7in1, DOS, USB Keyboard & USB optical Mouse| Case Mini',5650000.00,2013,4,36,12015,'/images/42682.jpg'),
(40753,4,2,'PC HP PRO 280MT (L0J17PA) (G3250/ 2G/ 500GB/ DVD-RW/ K/M)',N'CPU Intel Pentium Dual Core G3250 (2x3.3Ghz, 3MB L2, GPU)| Intel H81 Express, 2GB DDR3 1600 (2 slot, Upto 16GB)| 500GB SATA 7200RPM, DVD-RW SATA, Integrated| 3 PCI Express x1, 1 PCI Express x16 slot| 10/100/1000 Realtek RTL8151GH, 2 xPS/2| 6xUSB 2.0, 2xUSB 3.0, 1xVGA, 1xDVI| 1 x COM, Ubuntu, (USB) HP Keyboard & Optical Mouse. 7.05kg| Case form lớn',8650000.00,2015,12,36,0,'/images/40753.jpg');
go

