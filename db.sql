USE [master]
GO
/****** Object:  Database [Assignment1_HuynhGiaBao]    Script Date: 03/11/2021 23:11:14 PM ******/
CREATE DATABASE [Assignment1_HuynhGiaBao]
 CONTAINMENT = NONE
 ON  PRIMARY 
( NAME = N'Assignment1_HuynhGiaBao', FILENAME = N'C:\Program Files\Microsoft SQL Server\MSSQL15.SQLEXPRESS\MSSQL\DATA\Assignment1_HuynhGiaBao.mdf' , SIZE = 8192KB , MAXSIZE = UNLIMITED, FILEGROWTH = 65536KB )
 LOG ON 
( NAME = N'Assignment1_HuynhGiaBao_log', FILENAME = N'C:\Program Files\Microsoft SQL Server\MSSQL15.SQLEXPRESS\MSSQL\DATA\Assignment1_HuynhGiaBao_log.ldf' , SIZE = 8192KB , MAXSIZE = 2048GB , FILEGROWTH = 65536KB )
 WITH CATALOG_COLLATION = DATABASE_DEFAULT
GO
ALTER DATABASE [Assignment1_HuynhGiaBao] SET COMPATIBILITY_LEVEL = 150
GO
IF (1 = FULLTEXTSERVICEPROPERTY('IsFullTextInstalled'))
begin
EXEC [Assignment1_HuynhGiaBao].[dbo].[sp_fulltext_database] @action = 'enable'
end
GO
ALTER DATABASE [Assignment1_HuynhGiaBao] SET ANSI_NULL_DEFAULT OFF 
GO
ALTER DATABASE [Assignment1_HuynhGiaBao] SET ANSI_NULLS OFF 
GO
ALTER DATABASE [Assignment1_HuynhGiaBao] SET ANSI_PADDING OFF 
GO
ALTER DATABASE [Assignment1_HuynhGiaBao] SET ANSI_WARNINGS OFF 
GO
ALTER DATABASE [Assignment1_HuynhGiaBao] SET ARITHABORT OFF 
GO
ALTER DATABASE [Assignment1_HuynhGiaBao] SET AUTO_CLOSE ON 
GO
ALTER DATABASE [Assignment1_HuynhGiaBao] SET AUTO_SHRINK OFF 
GO
ALTER DATABASE [Assignment1_HuynhGiaBao] SET AUTO_UPDATE_STATISTICS ON 
GO
ALTER DATABASE [Assignment1_HuynhGiaBao] SET CURSOR_CLOSE_ON_COMMIT OFF 
GO
ALTER DATABASE [Assignment1_HuynhGiaBao] SET CURSOR_DEFAULT  GLOBAL 
GO
ALTER DATABASE [Assignment1_HuynhGiaBao] SET CONCAT_NULL_YIELDS_NULL OFF 
GO
ALTER DATABASE [Assignment1_HuynhGiaBao] SET NUMERIC_ROUNDABORT OFF 
GO
ALTER DATABASE [Assignment1_HuynhGiaBao] SET QUOTED_IDENTIFIER OFF 
GO
ALTER DATABASE [Assignment1_HuynhGiaBao] SET RECURSIVE_TRIGGERS OFF 
GO
ALTER DATABASE [Assignment1_HuynhGiaBao] SET  ENABLE_BROKER 
GO
ALTER DATABASE [Assignment1_HuynhGiaBao] SET AUTO_UPDATE_STATISTICS_ASYNC OFF 
GO
ALTER DATABASE [Assignment1_HuynhGiaBao] SET DATE_CORRELATION_OPTIMIZATION OFF 
GO
ALTER DATABASE [Assignment1_HuynhGiaBao] SET TRUSTWORTHY OFF 
GO
ALTER DATABASE [Assignment1_HuynhGiaBao] SET ALLOW_SNAPSHOT_ISOLATION OFF 
GO
ALTER DATABASE [Assignment1_HuynhGiaBao] SET PARAMETERIZATION SIMPLE 
GO
ALTER DATABASE [Assignment1_HuynhGiaBao] SET READ_COMMITTED_SNAPSHOT OFF 
GO
ALTER DATABASE [Assignment1_HuynhGiaBao] SET HONOR_BROKER_PRIORITY OFF 
GO
ALTER DATABASE [Assignment1_HuynhGiaBao] SET RECOVERY SIMPLE 
GO
ALTER DATABASE [Assignment1_HuynhGiaBao] SET  MULTI_USER 
GO
ALTER DATABASE [Assignment1_HuynhGiaBao] SET PAGE_VERIFY CHECKSUM  
GO
ALTER DATABASE [Assignment1_HuynhGiaBao] SET DB_CHAINING OFF 
GO
ALTER DATABASE [Assignment1_HuynhGiaBao] SET FILESTREAM( NON_TRANSACTED_ACCESS = OFF ) 
GO
ALTER DATABASE [Assignment1_HuynhGiaBao] SET TARGET_RECOVERY_TIME = 60 SECONDS 
GO
ALTER DATABASE [Assignment1_HuynhGiaBao] SET DELAYED_DURABILITY = DISABLED 
GO
ALTER DATABASE [Assignment1_HuynhGiaBao] SET ACCELERATED_DATABASE_RECOVERY = OFF  
GO
ALTER DATABASE [Assignment1_HuynhGiaBao] SET QUERY_STORE = OFF
GO
USE [Assignment1_HuynhGiaBao]
GO
/****** Object:  Table [dbo].[tblCategories]    Script Date: 03/11/2021 23:11:14 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[tblCategories](
	[categoryID] [varchar](20) NOT NULL,
	[name] [nchar](10) NOT NULL,
 CONSTRAINT [PK_tblCategories] PRIMARY KEY CLUSTERED 
(
	[categoryID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[tblHistory]    Script Date: 03/11/2021 23:11:14 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[tblHistory](
	[historyID] [int] IDENTITY(1,1) NOT NULL,
	[date] [datetime] NOT NULL,
	[userID] [varchar](50) NOT NULL,
	[productID] [varchar](20) NOT NULL,
	[action] [nvarchar](20) NOT NULL,
	[content] [nvarchar](300) NOT NULL,
 CONSTRAINT [PK_tblHistory] PRIMARY KEY CLUSTERED 
(
	[historyID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[tblOrderDetails]    Script Date: 03/11/2021 23:11:14 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[tblOrderDetails](
	[orderDetailID] [int] IDENTITY(1,1) NOT NULL,
	[productID] [varchar](20) NOT NULL,
	[quantity] [int] NOT NULL,
	[price] [float] NOT NULL,
	[orderID] [int] NOT NULL,
 CONSTRAINT [PK_tblOrderDetails] PRIMARY KEY CLUSTERED 
(
	[orderDetailID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[tblOrders]    Script Date: 03/11/2021 23:11:14 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[tblOrders](
	[orderID] [int] IDENTITY(1,1) NOT NULL,
	[userID] [varchar](50) NOT NULL,
	[total] [float] NOT NULL,
	[date] [datetime] NOT NULL,
	[payment] [varchar](50) NULL,
 CONSTRAINT [PK_tblOrders] PRIMARY KEY CLUSTERED 
(
	[orderID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[tblProducts]    Script Date: 03/11/2021 23:11:14 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[tblProducts](
	[productID] [varchar](20) NOT NULL,
	[name] [nvarchar](50) NOT NULL,
	[quantity] [int] NOT NULL,
	[price] [float] NOT NULL,
	[date] [datetime] NOT NULL,
	[expiryDate] [datetime] NOT NULL,
	[imgLink] [varchar](300) NULL,
	[description] [nvarchar](300) NOT NULL,
	[categoryID] [varchar](20) NOT NULL,
	[status] [bit] NOT NULL,
 CONSTRAINT [PK_tblProducts] PRIMARY KEY CLUSTERED 
(
	[productID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[tblRoles]    Script Date: 03/11/2021 23:11:14 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[tblRoles](
	[roleID] [varchar](10) NOT NULL,
	[name] [nvarchar](20) NOT NULL,
 CONSTRAINT [PK_tblRoles] PRIMARY KEY CLUSTERED 
(
	[roleID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[tblUsers]    Script Date: 03/11/2021 23:11:14 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[tblUsers](
	[userID] [varchar](50) NOT NULL,
	[name] [nvarchar](50) NOT NULL,
	[password] [varchar](50) NOT NULL,
	[email] [varchar](50) NOT NULL,
	[phone] [varchar](15) NOT NULL,
	[address] [nvarchar](100) NOT NULL,
	[status] [bit] NOT NULL,
	[roleID] [varchar](10) NOT NULL,
 CONSTRAINT [PK_tblUsers] PRIMARY KEY CLUSTERED 
(
	[userID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
INSERT [dbo].[tblCategories] ([categoryID], [name]) VALUES (N'mat-na', N'Mặt Nạ    ')
INSERT [dbo].[tblCategories] ([categoryID], [name]) VALUES (N'sua-duong', N'Sữa Dưỡng ')
GO
SET IDENTITY_INSERT [dbo].[tblOrderDetails] ON 

INSERT [dbo].[tblOrderDetails] ([orderDetailID], [productID], [quantity], [price], [orderID]) VALUES (51, N'1', 1, 599000, 29)
SET IDENTITY_INSERT [dbo].[tblOrderDetails] OFF
GO
SET IDENTITY_INSERT [dbo].[tblOrders] ON 

INSERT [dbo].[tblOrders] ([orderID], [userID], [total], [date], [payment]) VALUES (29, N'user', 599000, CAST(N'2021-11-03T22:28:32.053' AS DateTime), NULL)
SET IDENTITY_INSERT [dbo].[tblOrders] OFF
GO
INSERT [dbo].[tblProducts] ([productID], [name], [quantity], [price], [date], [expiryDate], [imgLink], [description], [categoryID], [status]) VALUES (N'1', N'Sữa Dưỡng Ẩm Mịn Da', 96, 599000, CAST(N'2021-11-03T22:11:40.670' AS DateTime), CAST(N'2022-11-03T00:00:00.000' AS DateTime), N'https://i.imgur.com/1kmPKUw.png', N'The Fresh For Men Hydrating Emulsion là sữa dưỡng ẩm vượt trội, giúp da cân bằng ẩm và trở nên mịn màng mà THEFACESHOP dành riêng cho nam giới.', N'sua-duong', 1)
INSERT [dbo].[tblProducts] ([productID], [name], [quantity], [price], [date], [expiryDate], [imgLink], [description], [categoryID], [status]) VALUES (N'2', N'Mặt Nạ Thanh Lọc Da', 100, 300000, CAST(N'2021-11-03T22:33:45.777' AS DateTime), CAST(N'2022-11-03T00:00:00.000' AS DateTime), N'https://i.imgur.com/cTGY6l4.png', N'Tinh chất dầu tràm dồi dào có trong Real Nature Tea Tree Face Mask của THEFACESHOP sẽ giúp kháng khuẩn và thanh lọc làn da của bạn một cách hiệu quả, vừa giúp da sạch mụn lại vừa hỗ trợ da sáng khỏe hẳn lên từng ngày', N'mat-na', 1)
GO
INSERT [dbo].[tblRoles] ([roleID], [name]) VALUES (N'AD', N'Admin')
INSERT [dbo].[tblRoles] ([roleID], [name]) VALUES (N'Google', N'Goole User')
INSERT [dbo].[tblRoles] ([roleID], [name]) VALUES (N'US', N'User')
GO
INSERT [dbo].[tblUsers] ([userID], [name], [password], [email], [phone], [address], [status], [roleID]) VALUES (N'admin', N'Admin nè', N'123456', N'admin@gmail.com', N'0123123123', N'HN', 1, N'AD')
INSERT [dbo].[tblUsers] ([userID], [name], [password], [email], [phone], [address], [status], [roleID]) VALUES (N'test', N'Huỳnh Bảo', N'123456', N'hb@gmail.com', N'0123456789', N'HCM', 1, N'US')
INSERT [dbo].[tblUsers] ([userID], [name], [password], [email], [phone], [address], [status], [roleID]) VALUES (N'user', N'User nè', N'123456', N'abc@gmail.com', N'0123456789', N'HCM', 1, N'US')
GO
ALTER TABLE [dbo].[tblHistory] ADD  CONSTRAINT [DF_tblHistory_date]  DEFAULT (getdate()) FOR [date]
GO
ALTER TABLE [dbo].[tblOrders] ADD  CONSTRAINT [DF_tblOrders_date]  DEFAULT (getdate()) FOR [date]
GO
ALTER TABLE [dbo].[tblProducts] ADD  CONSTRAINT [DF_tblProducts_date]  DEFAULT (getdate()) FOR [date]
GO
ALTER TABLE [dbo].[tblProducts] ADD  CONSTRAINT [DF_tblProducts_expiryDate]  DEFAULT (getdate()) FOR [expiryDate]
GO
ALTER TABLE [dbo].[tblProducts] ADD  CONSTRAINT [DF_tblProducts_status]  DEFAULT ((1)) FOR [status]
GO
ALTER TABLE [dbo].[tblUsers] ADD  CONSTRAINT [DF_tblUsers_status]  DEFAULT ((1)) FOR [status]
GO
ALTER TABLE [dbo].[tblHistory]  WITH CHECK ADD  CONSTRAINT [FK_tblHistory_tblProducts] FOREIGN KEY([productID])
REFERENCES [dbo].[tblProducts] ([productID])
GO
ALTER TABLE [dbo].[tblHistory] CHECK CONSTRAINT [FK_tblHistory_tblProducts]
GO
ALTER TABLE [dbo].[tblHistory]  WITH CHECK ADD  CONSTRAINT [FK_tblHistory_tblUsers] FOREIGN KEY([userID])
REFERENCES [dbo].[tblUsers] ([userID])
GO
ALTER TABLE [dbo].[tblHistory] CHECK CONSTRAINT [FK_tblHistory_tblUsers]
GO
ALTER TABLE [dbo].[tblOrders]  WITH CHECK ADD  CONSTRAINT [FK_tblOrders_tblUsers] FOREIGN KEY([userID])
REFERENCES [dbo].[tblUsers] ([userID])
GO
ALTER TABLE [dbo].[tblOrders] CHECK CONSTRAINT [FK_tblOrders_tblUsers]
GO
ALTER TABLE [dbo].[tblProducts]  WITH CHECK ADD  CONSTRAINT [FK_tblProducts_tblCategories] FOREIGN KEY([categoryID])
REFERENCES [dbo].[tblCategories] ([categoryID])
GO
ALTER TABLE [dbo].[tblProducts] CHECK CONSTRAINT [FK_tblProducts_tblCategories]
GO
ALTER TABLE [dbo].[tblUsers]  WITH CHECK ADD  CONSTRAINT [FK_tblUsers_tblRoles] FOREIGN KEY([roleID])
REFERENCES [dbo].[tblRoles] ([roleID])
GO
ALTER TABLE [dbo].[tblUsers] CHECK CONSTRAINT [FK_tblUsers_tblRoles]
GO
USE [master]
GO
ALTER DATABASE [Assignment1_HuynhGiaBao] SET  READ_WRITE 
GO
