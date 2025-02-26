IF NOT EXISTS (SELECT name FROM sys.databases WHERE name = 'MegaCityCab')
BEGIN
    CREATE DATABASE MegaCityCab;
END;
USE [MegaCityCab]
GO
/****** Object:  Table [dbo].[booking_assignments]    Script Date: 2/27/2025 12:46:54 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[booking_assignments](
    [id] [int] IDENTITY(1,1) NOT NULL,
    [booking_id] [int] NOT NULL,
    [driver_id] [int] NULL,
    [vehicle_id] [int] NULL,
    [assigned_at] [datetime] NULL,
    PRIMARY KEY CLUSTERED
(
[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
    ) ON [PRIMARY]
    GO
/****** Object:  Table [dbo].[bookings]    Script Date: 2/27/2025 12:46:54 AM ******/
    SET ANSI_NULLS ON
    GO
    SET QUOTED_IDENTIFIER ON
    GO
CREATE TABLE [dbo].[bookings](
    [id] [int] IDENTITY(1,1) NOT NULL,
    [order_number] [varchar](20) NOT NULL,
    [customer_id] [int] NOT NULL,
    [destination_details] [text] NOT NULL,
    [booking_date] [datetime] NOT NULL,
    [status] [varchar](20) NULL,
    [total_amount] [decimal](10, 2) NULL,
    [pickup_location] [varchar](255) NULL,
    PRIMARY KEY CLUSTERED
(
[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
    ) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
    GO
/****** Object:  Table [dbo].[drivers]    Script Date: 2/27/2025 12:46:54 AM ******/
    SET ANSI_NULLS ON
    GO
    SET QUOTED_IDENTIFIER ON
    GO
CREATE TABLE [dbo].[drivers](
    [id] [int] IDENTITY(1,1) NOT NULL,
    [user_id] [int] NOT NULL,
    [license_number] [varchar](50) NOT NULL,
    [verified] [varchar](10) NOT NULL,
    [status] [varchar](20) NOT NULL,
    [created_at] [datetime] NULL,
    PRIMARY KEY CLUSTERED
(
[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
    ) ON [PRIMARY]
    GO
/****** Object:  Table [dbo].[payment]    Script Date: 2/27/2025 12:46:54 AM ******/
    SET ANSI_NULLS ON
    GO
    SET QUOTED_IDENTIFIER ON
    GO
CREATE TABLE [dbo].[payment](
    [id] [int] IDENTITY(1,1) NOT NULL,
    [booking_id] [int] NOT NULL,
    [customer_id] [int] NOT NULL,
    [card_number] [varchar](16) NOT NULL,
    [card_holder_name] [varchar](100) NOT NULL,
    [expiry_date] [varchar](7) NOT NULL,
    [cvv] [varchar](4) NOT NULL,
    [amount] [decimal](10, 2) NOT NULL,
    [payment_date] [datetime] NULL,
    [status] [varchar](50) NULL,
    PRIMARY KEY CLUSTERED
(
[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
    ) ON [PRIMARY]
    GO
/****** Object:  Table [dbo].[users]    Script Date: 2/27/2025 12:46:54 AM ******/
    SET ANSI_NULLS ON
    GO
    SET QUOTED_IDENTIFIER ON
    GO
CREATE TABLE [dbo].[users](
    [id] [int] IDENTITY(1,1) NOT NULL,
    [username] [varchar](50) NOT NULL,
    [password] [varchar](255) NOT NULL,
    [name] [varchar](100) NOT NULL,
    [address] [text] NOT NULL,
    [nic] [varchar](12) NOT NULL,
    [phone] [varchar](15) NOT NULL,
    [role] [varchar](20) NOT NULL,
    [created_at] [datetime] NULL,
    [email] [nvarchar](255) NOT NULL,
    PRIMARY KEY CLUSTERED
(
[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
    ) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
    GO
/****** Object:  Table [dbo].[vehicles]    Script Date: 2/27/2025 12:46:54 AM ******/
    SET ANSI_NULLS ON
    GO
    SET QUOTED_IDENTIFIER ON
    GO
CREATE TABLE [dbo].[vehicles](
    [id] [int] IDENTITY(1,1) NOT NULL,
    [license_plate] [varchar](20) NOT NULL,
    [model] [varchar](50) NOT NULL,
    [manufacturer] [varchar](50) NOT NULL,
    [vehicle_type] [varchar](20) NOT NULL,
    [year] [int] NOT NULL,
    [capacity] [int] NOT NULL,
    [status] [varchar](20) NOT NULL,
    [created_at] [datetime] NULL,
    PRIMARY KEY CLUSTERED
(
[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
    ) ON [PRIMARY]
    GO
    SET IDENTITY_INSERT [dbo].[booking_assignments] ON

    INSERT [dbo].[booking_assignments] ([id], [booking_id], [driver_id], [vehicle_id], [assigned_at]) VALUES (16, 16, 12, 10, CAST(N'2025-02-22T21:50:18.350' AS DateTime))
    INSERT [dbo].[booking_assignments] ([id], [booking_id], [driver_id], [vehicle_id], [assigned_at]) VALUES (17, 17, 12, 11, CAST(N'2025-02-22T21:58:40.327' AS DateTime))
    INSERT [dbo].[booking_assignments] ([id], [booking_id], [driver_id], [vehicle_id], [assigned_at]) VALUES (18, 18, 16, 10, CAST(N'2025-02-23T16:24:33.200' AS DateTime))
    INSERT [dbo].[booking_assignments] ([id], [booking_id], [driver_id], [vehicle_id], [assigned_at]) VALUES (1009, 1009, 16, 11, CAST(N'2025-02-25T01:13:04.167' AS DateTime))
    INSERT [dbo].[booking_assignments] ([id], [booking_id], [driver_id], [vehicle_id], [assigned_at]) VALUES (1010, 1028, 12, 8, CAST(N'2025-02-26T04:59:10.593' AS DateTime))
    SET IDENTITY_INSERT [dbo].[booking_assignments] OFF
    GO
    SET IDENTITY_INSERT [dbo].[bookings] ON

    INSERT [dbo].[bookings] ([id], [order_number], [customer_id], [destination_details], [booking_date], [status], [total_amount], [pickup_location]) VALUES (16, N'ORD-1740241218322', 22, N'nuwara eliya', CAST(N'2025-03-01T21:50:00.000' AS DateTime), N'COMPLETED', CAST(110.22 AS Decimal(10, 2)), N'balangoda town')
    INSERT [dbo].[bookings] ([id], [order_number], [customer_id], [destination_details], [booking_date], [status], [total_amount], [pickup_location]) VALUES (17, N'ORD-1740241720301', 22, N'nuwara eliya', CAST(N'2025-02-22T13:58:00.000' AS DateTime), N'COMPLETED', CAST(82.31 AS Decimal(10, 2)), N'balangoda town')
    INSERT [dbo].[bookings] ([id], [order_number], [customer_id], [destination_details], [booking_date], [status], [total_amount], [pickup_location]) VALUES (18, N'ORD-1740308073185', 22, N'balangoda', CAST(N'2025-02-14T16:24:00.000' AS DateTime), N'Cancelled', CAST(39.95 AS Decimal(10, 2)), N'balangoda town')
    INSERT [dbo].[bookings] ([id], [order_number], [customer_id], [destination_details], [booking_date], [status], [total_amount], [pickup_location]) VALUES (1009, N'ORD-1740426184131', 22, N'nuwara eliya', CAST(N'2025-02-25T01:16:00.000' AS DateTime), N'Cancelled', CAST(100.43 AS Decimal(10, 2)), N'balangoda town')
    INSERT [dbo].[bookings] ([id], [order_number], [customer_id], [destination_details], [booking_date], [status], [total_amount], [pickup_location]) VALUES (1028, N'ORD-1740526150571', 22, N'Colombo', CAST(N'2025-02-22T04:50:00.000' AS DateTime), N'Cancelled', CAST(106.58 AS Decimal(10, 2)), N'Kandy')
    INSERT [dbo].[bookings] ([id], [order_number], [customer_id], [destination_details], [booking_date], [status], [total_amount], [pickup_location]) VALUES (1029, N'ORD-1740526645769', 22, N'Colombo', CAST(N'2025-02-27T05:07:00.000' AS DateTime), N'COMPLETED', CAST(64.06 AS Decimal(10, 2)), N'Kandy')
    INSERT [dbo].[bookings] ([id], [order_number], [customer_id], [destination_details], [booking_date], [status], [total_amount], [pickup_location]) VALUES (1030, N'ORD-1740527201622', 22, N'Colombo', CAST(N'2025-02-27T05:16:00.000' AS DateTime), N'APPROVED', CAST(67.31 AS Decimal(10, 2)), N'Kandy')
    SET IDENTITY_INSERT [dbo].[bookings] OFF
    GO
    SET IDENTITY_INSERT [dbo].[drivers] ON

    INSERT [dbo].[drivers] ([id], [user_id], [license_number], [verified], [status], [created_at]) VALUES (12, 37, N'1234545', N'Yes', N'On Trip', CAST(N'2025-02-11T02:03:29.110' AS DateTime))
    INSERT [dbo].[drivers] ([id], [user_id], [license_number], [verified], [status], [created_at]) VALUES (16, 42, N'JHG12345', N'NO', N'Inactive', CAST(N'2025-02-23T16:23:48.843' AS DateTime))
    SET IDENTITY_INSERT [dbo].[drivers] OFF
    GO
    SET IDENTITY_INSERT [dbo].[users] ON

    INSERT [dbo].[users] ([id], [username], [password], [name], [address], [nic], [phone], [role], [created_at], [email]) VALUES (22, N'himan', N'x3Xnt1ft5jDNCqERO9ECZhqziCnKUqZCKreChi8mhkY=', N'Himan Manduja', N'Sri Lanka', N'20038503504', N'330-243-5997', N'Customer', CAST(N'2025-01-26T17:27:54.573' AS DateTime), N'hghimanmanduja@gmail.com')
    INSERT [dbo].[users] ([id], [username], [password], [name], [address], [nic], [phone], [role], [created_at], [email]) VALUES (37, N'TestDriver', N'fypX9jTae7EmV/qQy8KY8Lfd8PTgbjRnGgvVevb60bk=', N'Test Driver', N'Test Address', N'200556503504', N'330-243-5921', N'Driver', CAST(N'2025-02-11T02:03:29.093' AS DateTime), N'hghimananduja@gmail.com')
    INSERT [dbo].[users] ([id], [username], [password], [name], [address], [nic], [phone], [role], [created_at], [email]) VALUES (38, N'Admin', N'FeKw08M4keuw8e9gnsQZQgwg4yDOlMZfvIwzEkSOsiU=', N'Himan Manduja', N'Sri Lanka', N'200312313070', N'330-243-4567', N'Admin', CAST(N'2025-02-21T16:49:33.870' AS DateTime), N'hghimanmanduja@admin.com')
    INSERT [dbo].[users] ([id], [username], [password], [name], [address], [nic], [phone], [role], [created_at], [email]) VALUES (42, N'Michael', N'fypX9jTae7EmV/qQy8KY8Lfd8PTgbjRnGgvVevb60bk=', N'Michael J Bragg', N'Dover, Ohio(OH), 44622', N'200558503999', N'330-243-5988', N'Driver', CAST(N'2025-02-23T16:23:48.840' AS DateTime), N'rebecca9710+schwarz@gmail.com')
    INSERT [dbo].[users] ([id], [username], [password], [name], [address], [nic], [phone], [role], [created_at], [email]) VALUES (191, N'testuser2', N'75K3eLr+dx6JJFuJ7LwIpEpOFmwGZZkRiB84PURz6U8=', N'Test User2', N'123 Test Street', N'1234567810V', N'0712345678', N'customer', CAST(N'2025-02-26T22:05:28.203' AS DateTime), N'test2@example.com')
    SET IDENTITY_INSERT [dbo].[users] OFF
    GO
    SET IDENTITY_INSERT [dbo].[vehicles] ON

    INSERT [dbo].[vehicles] ([id], [license_plate], [model], [manufacturer], [vehicle_type], [year], [capacity], [status], [created_at]) VALUES (7, N'ABC-1234', N'Sedan', N'Toyota', N'Sedan', 2020, 4, N'In Maintenance', CAST(N'2024-02-11T12:00:00.000' AS DateTime))
    INSERT [dbo].[vehicles] ([id], [license_plate], [model], [manufacturer], [vehicle_type], [year], [capacity], [status], [created_at]) VALUES (8, N'XYZ-5678', N'SUV', N'Honda', N'SUV', 2021, 5, N'Available', CAST(N'2024-02-11T12:05:00.000' AS DateTime))
    INSERT [dbo].[vehicles] ([id], [license_plate], [model], [manufacturer], [vehicle_type], [year], [capacity], [status], [created_at]) VALUES (10, N'DEF-2345', N'Motorbike', N'Yamaha', N'Motorbike', 2022, 2, N'Available', CAST(N'2024-02-11T12:15:00.000' AS DateTime))
    INSERT [dbo].[vehicles] ([id], [license_plate], [model], [manufacturer], [vehicle_type], [year], [capacity], [status], [created_at]) VALUES (11, N'GHI-6789', N'SUV', N'Ford', N'SUV', 2018, 5, N'Assigned', CAST(N'2024-02-11T12:20:00.000' AS DateTime))
    INSERT [dbo].[vehicles] ([id], [license_plate], [model], [manufacturer], [vehicle_type], [year], [capacity], [status], [created_at]) VALUES (18, N'123456', N'Sedan', N'Toyota', N'Sedan', 2012, 7, N'In Maintenance', CAST(N'2025-02-21T22:36:21.077' AS DateTime))
    INSERT [dbo].[vehicles] ([id], [license_plate], [model], [manufacturer], [vehicle_type], [year], [capacity], [status], [created_at]) VALUES (22, N'ABD-1234', N'RAV4', N'Toyota', N'SUV', 2022, 5, N'Available', CAST(N'2025-02-26T06:18:29.833' AS DateTime))
    INSERT [dbo].[vehicles] ([id], [license_plate], [model], [manufacturer], [vehicle_type], [year], [capacity], [status], [created_at]) VALUES (44, N'KHG-654', N'Supra', N'Toyota', N'Coupe', 2022, 4, N'Available', CAST(N'2025-02-26T08:04:44.187' AS DateTime))
    INSERT [dbo].[vehicles] ([id], [license_plate], [model], [manufacturer], [vehicle_type], [year], [capacity], [status], [created_at]) VALUES (56, N'ABC-123', N'Camry', N'Toyota', N'Sedan', 2022, 4, N'Available', CAST(N'2025-02-26T18:44:37.000' AS DateTime))
    INSERT [dbo].[vehicles] ([id], [license_plate], [model], [manufacturer], [vehicle_type], [year], [capacity], [status], [created_at]) VALUES (57, N'XYZ-456', N'CR-V', N'Honda', N'SUV', 2021, 5, N'Available', CAST(N'2025-02-26T18:44:37.073' AS DateTime))
    INSERT [dbo].[vehicles] ([id], [license_plate], [model], [manufacturer], [vehicle_type], [year], [capacity], [status], [created_at]) VALUES (58, N'DEF-789', N'Accord', N'Honda', N'Sedan', 2020, 4, N'Available', CAST(N'2025-02-26T18:44:37.153' AS DateTime))
    INSERT [dbo].[vehicles] ([id], [license_plate], [model], [manufacturer], [vehicle_type], [year], [capacity], [status], [created_at]) VALUES (59, N'GHI-111', N'F-150', N'Ford', N'Truck', 2019, 3, N'Available', CAST(N'2025-02-26T18:44:37.163' AS DateTime))
    INSERT [dbo].[vehicles] ([id], [license_plate], [model], [manufacturer], [vehicle_type], [year], [capacity], [status], [created_at]) VALUES (60, N'JKL-222', N'Odyssey', N'Honda', N'Van', 2018, 7, N'Available', CAST(N'2025-02-26T18:44:37.213' AS DateTime))
    INSERT [dbo].[vehicles] ([id], [license_plate], [model], [manufacturer], [vehicle_type], [year], [capacity], [status], [created_at]) VALUES (61, N'MNO-333', N'Mustang', N'Ford', N'Coupe', 2023, 2, N'Available', CAST(N'2025-02-26T18:44:37.253' AS DateTime))
    INSERT [dbo].[vehicles] ([id], [license_plate], [model], [manufacturer], [vehicle_type], [year], [capacity], [status], [created_at]) VALUES (62, N'PQR-444', N'X5', N'BMW', N'SUV', 2022, 5, N'Available', CAST(N'2025-02-26T18:44:37.293' AS DateTime))
    INSERT [dbo].[vehicles] ([id], [license_plate], [model], [manufacturer], [vehicle_type], [year], [capacity], [status], [created_at]) VALUES (63, N'STU-555', N'Civic', N'Honda', N'Sedan', 2017, 4, N'Available', CAST(N'2025-02-26T18:44:37.333' AS DateTime))
    INSERT [dbo].[vehicles] ([id], [license_plate], [model], [manufacturer], [vehicle_type], [year], [capacity], [status], [created_at]) VALUES (64, N'VWX-666', N'Rav4', N'Toyota', N'SUV', 2019, 5, N'Available', CAST(N'2025-02-26T18:44:37.347' AS DateTime))
    INSERT [dbo].[vehicles] ([id], [license_plate], [model], [manufacturer], [vehicle_type], [year], [capacity], [status], [created_at]) VALUES (65, N'YZA-777', N'Golf', N'Volkswagen', N'Hatchback', 2018, 4, N'Available', CAST(N'2025-02-26T18:44:37.380' AS DateTime))
    INSERT [dbo].[vehicles] ([id], [license_plate], [model], [manufacturer], [vehicle_type], [year], [capacity], [status], [created_at]) VALUES (67, N'XYZ987', N'CR-V', N'Honda', N'SUV', 2021, 5, N'Available', CAST(N'2025-02-26T18:48:57.867' AS DateTime))
    INSERT [dbo].[vehicles] ([id], [license_plate], [model], [manufacturer], [vehicle_type], [year], [capacity], [status], [created_at]) VALUES (69, N'PQR333', N'Civic', N'Honda', N'Sedan', 2023, 4, N'Available', CAST(N'2025-02-26T18:48:58.047' AS DateTime))
    INSERT [dbo].[vehicles] ([id], [license_plate], [model], [manufacturer], [vehicle_type], [year], [capacity], [status], [created_at]) VALUES (70, N'JKL111', N'Rav4', N'Toyota', N'SUV', 2022, 5, N'Available', CAST(N'2025-02-26T18:48:58.130' AS DateTime))
    INSERT [dbo].[vehicles] ([id], [license_plate], [model], [manufacturer], [vehicle_type], [year], [capacity], [status], [created_at]) VALUES (71, N'MNO222', N'Camry', N'Toyota', N'Sedan', 2021, 4, N'Available', CAST(N'2025-02-26T18:48:58.143' AS DateTime))
    INSERT [dbo].[vehicles] ([id], [license_plate], [model], [manufacturer], [vehicle_type], [year], [capacity], [status], [created_at]) VALUES (72, N'DEF456', N'Focus', N'Ford', N'Hatchback', 2020, 4, N'Available', CAST(N'2025-02-26T18:48:58.170' AS DateTime))
    INSERT [dbo].[vehicles] ([id], [license_plate], [model], [manufacturer], [vehicle_type], [year], [capacity], [status], [created_at]) VALUES (74, N'GHI789', N'F-150', N'Ford', N'Truck', 2019, 2, N'Available', CAST(N'2025-02-26T18:48:58.213' AS DateTime))
    SET IDENTITY_INSERT [dbo].[vehicles] OFF
    GO
    SET ANSI_PADDING ON
    GO
/****** Object:  Index [UQ__bookings__730E34DF515B4325]    Script Date: 2/27/2025 12:46:54 AM ******/
ALTER TABLE [dbo].[bookings] ADD UNIQUE NONCLUSTERED
    (
    [order_number] ASC
    )WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, SORT_IN_TEMPDB = OFF, IGNORE_DUP_KEY = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
    GO
/****** Object:  Index [UQ__drivers__B9BE370EDE1AED8B]    Script Date: 2/27/2025 12:46:54 AM ******/
ALTER TABLE [dbo].[drivers] ADD UNIQUE NONCLUSTERED
    (
    [user_id] ASC
    )WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, SORT_IN_TEMPDB = OFF, IGNORE_DUP_KEY = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
    GO
    SET ANSI_PADDING ON
    GO
/****** Object:  Index [UQ__drivers__D482A003F0566C1F]    Script Date: 2/27/2025 12:46:54 AM ******/
ALTER TABLE [dbo].[drivers] ADD UNIQUE NONCLUSTERED
    (
    [license_number] ASC
    )WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, SORT_IN_TEMPDB = OFF, IGNORE_DUP_KEY = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
    GO
    SET ANSI_PADDING ON
    GO
/****** Object:  Index [UQ__users__AB6E6164F3B09CAC]    Script Date: 2/27/2025 12:46:54 AM ******/
ALTER TABLE [dbo].[users] ADD UNIQUE NONCLUSTERED
    (
    [email] ASC
    )WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, SORT_IN_TEMPDB = OFF, IGNORE_DUP_KEY = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
    GO
    SET ANSI_PADDING ON
    GO
/****** Object:  Index [UQ__users__DF97D0F566051619]    Script Date: 2/27/2025 12:46:54 AM ******/
ALTER TABLE [dbo].[users] ADD UNIQUE NONCLUSTERED
    (
    [nic] ASC
    )WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, SORT_IN_TEMPDB = OFF, IGNORE_DUP_KEY = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
    GO
    SET ANSI_PADDING ON
    GO
/****** Object:  Index [UQ__users__F3DBC572FF3513CF]    Script Date: 2/27/2025 12:46:54 AM ******/
ALTER TABLE [dbo].[users] ADD UNIQUE NONCLUSTERED
    (
    [username] ASC
    )WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, SORT_IN_TEMPDB = OFF, IGNORE_DUP_KEY = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
    GO
    SET ANSI_PADDING ON
    GO
/****** Object:  Index [UQ__vehicles__F72CD56E0EC1A76A]    Script Date: 2/27/2025 12:46:54 AM ******/
ALTER TABLE [dbo].[vehicles] ADD UNIQUE NONCLUSTERED
    (
    [license_plate] ASC
    )WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, SORT_IN_TEMPDB = OFF, IGNORE_DUP_KEY = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
    GO
ALTER TABLE [dbo].[booking_assignments] ADD  DEFAULT (getdate()) FOR [assigned_at]
    GO
ALTER TABLE [dbo].[bookings] ADD  DEFAULT ('PENDING') FOR [status]
    GO
ALTER TABLE [dbo].[drivers] ADD  DEFAULT ('No') FOR [verified]
    GO
ALTER TABLE [dbo].[drivers] ADD  DEFAULT ('Available') FOR [status]
    GO
ALTER TABLE [dbo].[drivers] ADD  DEFAULT (getdate()) FOR [created_at]
    GO
ALTER TABLE [dbo].[payment] ADD  DEFAULT (getdate()) FOR [payment_date]
    GO
ALTER TABLE [dbo].[payment] ADD  DEFAULT ('Completed') FOR [status]
    GO
ALTER TABLE [dbo].[users] ADD  DEFAULT (getdate()) FOR [created_at]
    GO
ALTER TABLE [dbo].[vehicles] ADD  DEFAULT ('Available') FOR [status]
    GO
ALTER TABLE [dbo].[vehicles] ADD  DEFAULT (getdate()) FOR [created_at]
    GO
ALTER TABLE [dbo].[booking_assignments]  WITH CHECK ADD FOREIGN KEY([booking_id])
    REFERENCES [dbo].[bookings] ([id])
    ON DELETE CASCADE
GO
ALTER TABLE [dbo].[booking_assignments]  WITH CHECK ADD FOREIGN KEY([driver_id])
    REFERENCES [dbo].[drivers] ([id])
    GO
ALTER TABLE [dbo].[booking_assignments]  WITH CHECK ADD FOREIGN KEY([vehicle_id])
    REFERENCES [dbo].[vehicles] ([id])
    GO
ALTER TABLE [dbo].[bookings]  WITH CHECK ADD FOREIGN KEY([customer_id])
    REFERENCES [dbo].[users] ([id])
    ON DELETE CASCADE
GO
ALTER TABLE [dbo].[drivers]  WITH CHECK ADD FOREIGN KEY([user_id])
    REFERENCES [dbo].[users] ([id])
    ON DELETE CASCADE
GO
ALTER TABLE [dbo].[payment]  WITH CHECK ADD  CONSTRAINT [FK_Payment_Booking] FOREIGN KEY([booking_id])
    REFERENCES [dbo].[bookings] ([id])
    GO
ALTER TABLE [dbo].[payment] CHECK CONSTRAINT [FK_Payment_Booking]
    GO
ALTER TABLE [dbo].[payment]  WITH CHECK ADD  CONSTRAINT [FK_Payment_Customer] FOREIGN KEY([customer_id])
    REFERENCES [dbo].[users] ([id])
    GO
ALTER TABLE [dbo].[payment] CHECK CONSTRAINT [FK_Payment_Customer]
    GO
ALTER TABLE [dbo].[bookings]  WITH CHECK ADD  CONSTRAINT [CK_Status] CHECK  (([status]='APPROVED' OR [status]='CANCELLED' OR [status]='COMPLETED' OR [status]='PENDING'))
    GO
ALTER TABLE [dbo].[bookings] CHECK CONSTRAINT [CK_Status]
    GO
ALTER TABLE [dbo].[drivers]  WITH CHECK ADD CHECK  (([status]='Inactive' OR [status]='On Trip' OR [status]='Available'))
    GO
ALTER TABLE [dbo].[drivers]  WITH CHECK ADD CHECK  (([verified]='No' OR [verified]='Yes'))
    GO
ALTER TABLE [dbo].[users]  WITH CHECK ADD  CONSTRAINT [chk_role_enum] CHECK  (([role]='Driver' OR [role]='Admin' OR [role]='Customer'))
    GO
ALTER TABLE [dbo].[users] CHECK CONSTRAINT [chk_role_enum]
    GO
ALTER TABLE [dbo].[vehicles]  WITH CHECK ADD CHECK  (([status]='In Maintenance' OR [status]='Assigned' OR [status]='Available'))
    GO
ALTER TABLE [dbo].[vehicles]  WITH CHECK ADD  CONSTRAINT [CK_vehicles__vehicle_type] CHECK  (([vehicle_type]='Motorbike' OR [vehicle_type]='Van' OR [vehicle_type]='SUV' OR [vehicle_type]='Sedan' OR [vehicle_type]='Hatchback' OR [vehicle_type]='Convertible' OR [vehicle_type]='Coupe' OR [vehicle_type]='Pickup Truck' OR [vehicle_type]='Minivan' OR [vehicle_type]='Electric' OR [vehicle_type]='Hybrid' OR [vehicle_type]='Luxury' OR [vehicle_type]='Bus' OR [vehicle_type]='Truck'))
    GO
ALTER TABLE [dbo].[vehicles] CHECK CONSTRAINT [CK_vehicles__vehicle_type]
    GO
    EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'Check vehicle types' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'vehicles', @level2type=N'CONSTRAINT',@level2name=N'CK_vehicles__vehicle_type'
    GO
