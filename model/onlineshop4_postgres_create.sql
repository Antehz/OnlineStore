CREATE TABLE "customer" (
	"id" serial NOT NULL,
	"login" character varying(20) NOT NULL UNIQUE,
	"password" character varying(20) NOT NULL,
	"role" int NOT NULL,
	CONSTRAINT customer_pk PRIMARY KEY ("id")
) WITH (
  OIDS=FALSE
);



CREATE TABLE "customer_account" (
	"id" serial NOT NULL,
	"first_name" character varying(30) NOT NULL,
	"last_name" character varying(30) NOT NULL,
	"email" character varying(20) NOT NULL UNIQUE,
	"created" TIMESTAMP NOT NULL,
	"date_birth" TIMESTAMP NOT NULL,
	"gender" int NOT NULL,
	CONSTRAINT customer_account_pk PRIMARY KEY ("id")
) WITH (
  OIDS=FALSE
);



CREATE TABLE "customer_address" (
	"id" serial NOT NULL,
	"country" character varying(20) NOT NULL,
	"city" character varying(20) NOT NULL,
	"adresss" character varying(50) NOT NULL,
	"zip_code" character varying NOT NULL,
	CONSTRAINT customer_address_pk PRIMARY KEY ("id")
) WITH (
  OIDS=FALSE
);



CREATE TABLE "customer_rating" (
	"id" serial NOT NULL,
	"count_order" int NOT NULL,
	"total_summ" bigint NOT NULL,
	CONSTRAINT customer_rating_pk PRIMARY KEY ("id")
) WITH (
  OIDS=FALSE
);



CREATE TABLE "product" (
	"id" serial NOT NULL,
	"category_id" int NOT NULL,
	"name" character varying(150) NOT NULL,
	"manufacturer" character varying NOT NULL,
	"model" character varying NOT NULL,
	"price" int NOT NULL,
	"description" character varying(200) NOT NULL,
	"image" character varying(1200) NOT NULL,
	"count_order" int NOT NULL,
	"count_recommended" int NOT NULL,
	"available" int NOT NULL,
	CONSTRAINT product_pk PRIMARY KEY ("id")
) WITH (
  OIDS=FALSE
);



CREATE TABLE "product_comment" (
	"id" serial NOT NULL,
	"id_product" int NOT NULL,
	"id_customer" int NOT NULL,
	"comment" character varying(1000) NOT NULL,
	"date" TIMESTAMP NOT NULL,
	CONSTRAINT product_comment_pk PRIMARY KEY ("id")
) WITH (
  OIDS=FALSE
);



CREATE TABLE "order" (
	"id" serial NOT NULL,
	"customer_id" int NOT NULL,
	"price" numeric NOT NULL,
	"start_date" TIMESTAMP NOT NULL,
	"end_date" TIMESTAMP NOT NULL,
	"shipping_method" int NOT NULL,
	"status" int NOT NULL,
	"total_price" bigint NOT NULL,
	CONSTRAINT order_pk PRIMARY KEY ("id")
) WITH (
  OIDS=FALSE
);



CREATE TABLE "order_content" (
	"id" serial NOT NULL,
	"order_id" int NOT NULL,
	"product_id" int NOT NULL,
	"price" bigint NOT NULL,
	"amount" int NOT NULL,
	"additional_wish" character varying NOT NULL,
	CONSTRAINT order_content_pk PRIMARY KEY ("id")
) WITH (
  OIDS=FALSE
);



CREATE TABLE "CATEGORY" (
	"id" serial NOT NULL,
	"parent_id" bigint NOT NULL,
	"name" character varying(20) NOT NULL UNIQUE,
	"description" character varying(200) NOT NULL,
	CONSTRAINT CATEGORY_pk PRIMARY KEY ("id")
) WITH (
  OIDS=FALSE
);



CREATE TABLE "cart_content" (
	"id" serial NOT NULL,
	"customer_id" int NOT NULL,
	"product_id" int NOT NULL,
	"amount" int NOT NULL,
	"date_add" TIMESTAMP NOT NULL,
	"price" int NOT NULL,
	CONSTRAINT cart_content_pk PRIMARY KEY ("id")
) WITH (
  OIDS=FALSE
);



CREATE TABLE "Tire" (
	"id" serial NOT NULL,
	"tire_destination" int NOT NULL,
	"tire_season" int NOT NULL,
	"profile_width" int NOT NULL,
	"profile_height" int NOT NULL,
	"rim_size" int NOT NULL,
	"spikes" BOOLEAN NOT NULL,
	CONSTRAINT Tire_pk PRIMARY KEY ("id")
) WITH (
  OIDS=FALSE
);



CREATE TABLE "wheel" (
	"id" serial NOT NULL,
	"wheel_type" int NOT NULL,
	"rim_size" int NOT NULL,
	"rim_width" int NOT NULL,
	"number_hole" int NOT NULL,
	"pcd" int NOT NULL,
	"dia" int NOT NULL,
	"et" int NOT NULL,
	CONSTRAINT wheel_pk PRIMARY KEY ("id")
) WITH (
  OIDS=FALSE
);



CREATE TABLE "car_battery" (
	"id" serial NOT NULL,
	"battery_type" int NOT NULL,
	"voltage" int NOT NULL,
	"capacity" int NOT NULL,
	"cca" int NOT NULL,
	"polarity" int NOT NULL,
	CONSTRAINT car_battery_pk PRIMARY KEY ("id")
) WITH (
  OIDS=FALSE
);



CREATE TABLE "lamp" (
	"id" serial NOT NULL,
	"lamp_type" int NOT NULL,
	"country_manufacturer" character varying NOT NULL,
	"cap" character varying NOT NULL,
	"power" int NOT NULL,
	"temperature" int NOT NULL,
	"luminous_flux" int NOT NULL,
	"package_contents" int NOT NULL,
	CONSTRAINT lamp_pk PRIMARY KEY ("id")
) WITH (
  OIDS=FALSE
);



CREATE TABLE "oil" (
	"id" serial NOT NULL,
	"oil_type" int NOT NULL,
	"oil_composition" int NOT NULL,
	"viscosity" character varying NOT NULL,
	"oil_destination" int NOT NULL,
	"volume" int NOT NULL,
	CONSTRAINT oil_pk PRIMARY KEY ("id")
) WITH (
  OIDS=FALSE
);



CREATE TABLE "coolant" (
	"id" serial NOT NULL,
	"fluid_state" int NOT NULL,
	"color" int NOT NULL,
	"volume" int NOT NULL,
	CONSTRAINT coolant_pk PRIMARY KEY ("id")
) WITH (
  OIDS=FALSE
);



CREATE TABLE "screen_wash" (
	"id" serial NOT NULL,
	"fluid_state" int NOT NULL,
	"wash_season" int NOT NULL,
	"temperature" int NOT NULL,
	"country_manufacturer" character varying NOT NULL,
	"volume" int NOT NULL,
	CONSTRAINT screen_wash_pk PRIMARY KEY ("id")
) WITH (
  OIDS=FALSE
);




ALTER TABLE "customer_account" ADD CONSTRAINT "customer_account_fk0" FOREIGN KEY ("id") REFERENCES "customer"("id");

ALTER TABLE "customer_address" ADD CONSTRAINT "customer_address_fk0" FOREIGN KEY ("id") REFERENCES "customer"("id");

ALTER TABLE "customer_rating" ADD CONSTRAINT "customer_rating_fk0" FOREIGN KEY ("id") REFERENCES "customer"("id");

ALTER TABLE "product" ADD CONSTRAINT "product_fk0" FOREIGN KEY ("category_id") REFERENCES "CATEGORY"("id");

ALTER TABLE "product_comment" ADD CONSTRAINT "product_comment_fk0" FOREIGN KEY ("id_product") REFERENCES "product"("id");
ALTER TABLE "product_comment" ADD CONSTRAINT "product_comment_fk1" FOREIGN KEY ("id_customer") REFERENCES "customer"("id");

ALTER TABLE "order" ADD CONSTRAINT "order_fk0" FOREIGN KEY ("customer_id") REFERENCES "customer"("id");

ALTER TABLE "order_content" ADD CONSTRAINT "order_content_fk0" FOREIGN KEY ("order_id") REFERENCES "order"("id");
ALTER TABLE "order_content" ADD CONSTRAINT "order_content_fk1" FOREIGN KEY ("product_id") REFERENCES "product"("id");

ALTER TABLE "CATEGORY" ADD CONSTRAINT "CATEGORY_fk0" FOREIGN KEY ("parent_id") REFERENCES "CATEGORY"("id");

ALTER TABLE "cart_content" ADD CONSTRAINT "cart_content_fk0" FOREIGN KEY ("customer_id") REFERENCES "customer"("id");
ALTER TABLE "cart_content" ADD CONSTRAINT "cart_content_fk1" FOREIGN KEY ("product_id") REFERENCES "product"("id");

ALTER TABLE "Tire" ADD CONSTRAINT "Tire_fk0" FOREIGN KEY ("id") REFERENCES "product"("id");

ALTER TABLE "wheel" ADD CONSTRAINT "wheel_fk0" FOREIGN KEY ("id") REFERENCES "product"("id");

ALTER TABLE "car_battery" ADD CONSTRAINT "car_battery_fk0" FOREIGN KEY ("id") REFERENCES "product"("id");

ALTER TABLE "lamp" ADD CONSTRAINT "lamp_fk0" FOREIGN KEY ("id") REFERENCES "product"("id");

ALTER TABLE "oil" ADD CONSTRAINT "oil_fk0" FOREIGN KEY ("id") REFERENCES "product"("id");

ALTER TABLE "coolant" ADD CONSTRAINT "coolant_fk0" FOREIGN KEY ("id") REFERENCES "product"("id");

ALTER TABLE "screen_wash" ADD CONSTRAINT "screen_wash_fk0" FOREIGN KEY ("id") REFERENCES "product"("id");

