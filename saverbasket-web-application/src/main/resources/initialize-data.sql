/**
 * Initialization script for SVB database schema
 * 
 */

-- Add full text capability for compare basket
-- ALTER TABLE productattribute ADD FULLTEXT(productMapValue);

-- Initialize sequences

DELETE FROM account_seq;
INSERT INTO account_seq(next_val) VALUES (NVL((SELECT MAX(idAccount)+1 FROM account),1));

DELETE FROM address_seq;
INSERT INTO address_seq(next_val) VALUES (NVL((SELECT MAX(idAddress)+1 FROM address),1));

DELETE FROM basket_seq;
INSERT INTO basket_seq(next_val) VALUES (NVL((SELECT MAX(idBasket)+1 FROM basket),1));

DELETE FROM basket_item_seq;
INSERT INTO basket_item_seq(next_val) VALUES (NVL((SELECT MAX(idBasketItem)+1 FROM basketitem),1));

DELETE FROM business_seq;
INSERT INTO business_seq(next_val) VALUES (NVL((SELECT MAX(idBusiness)+1 FROM business),1));

DELETE FROM business_rating_seq;
INSERT INTO business_rating_seq(next_val) VALUES (NVL((SELECT MAX(idBusinessRating)+1 FROM businessrating),1));

DELETE FROM campaign_seq;
INSERT INTO campaign_seq(next_val) VALUES (NVL((SELECT MAX(idCampaign)+1 FROM campaign),1));

DELETE FROM device_profile_seq;
INSERT INTO device_profile_seq(next_val) VALUES (NVL((SELECT MAX(idDeviceProfile)+1 FROM deviceprofile),1));

DELETE FROM follower_seq;
INSERT INTO follower_seq(next_val) VALUES (NVL((SELECT MAX(idFollower)+1 FROM follower),1));

DELETE FROM impression_seq;
INSERT INTO impression_seq(next_val) VALUES (NVL((SELECT MAX(idImpression)+1 FROM impression),1));

DELETE FROM privacy_info_seq;
INSERT INTO privacy_info_seq(next_val) VALUES (NVL((SELECT MAX(idPrivacyInformation)+1 FROM privacyinformation),1));

DELETE FROM product_attribute_seq;
INSERT INTO product_attribute_seq(next_val) VALUES (NVL((SELECT MAX(idProductAttribute)+1 FROM productattribute),1));

DELETE FROM product_type_seq;
INSERT INTO product_type_seq(next_val) VALUES (NVL((SELECT MAX(idProductType)+1 FROM producttype),1));

DELETE FROM credit_contribution_seq;
INSERT INTO credit_contribution_seq(next_val) VALUES (NVL((SELECT MAX(idCreditContribution)+1 FROM creditcontribution),1));

DELETE FROM subscription_seq;
INSERT INTO subscription_seq(next_val) VALUES (NVL((SELECT MAX(idSubscription)+1 FROM subscription),1));

DELETE FROM user_price_action_seq;
INSERT INTO user_price_action_seq(next_val) VALUES (NVL((SELECT MAX(idUserPriceAction)+1 FROM userpriceaction),1));

DELETE FROM user_prod_action_seq;
INSERT INTO user_prod_action_seq(next_val) VALUES (NVL((SELECT MAX(idUserProductAction)+1 FROM userproductaction),1));

DELETE FROM verification_token_seq;
INSERT INTO verification_token_seq(next_val) VALUES (NVL((SELECT MAX(idVerificationToken)+1 FROM verificationtoken),1));

DELETE FROM privilege_seq;
INSERT INTO privilege_seq(next_val) VALUES (NVL((SELECT MAX(idPrivilege)+1 FROM privilege),1));

DELETE FROM role_seq;
INSERT INTO role_seq(next_val) VALUES (NVL((SELECT MAX(idRole)+1 FROM role),1));

DELETE FROM plan_seq;
INSERT INTO plan_seq(next_val) VALUES (NVL((SELECT MAX(idPlan)+1 FROM plan),1));

DELETE FROM user_seq;
INSERT INTO user_seq(next_val) VALUES (NVL((SELECT MAX(idUser)+1 FROM user),1));

DELETE FROM credit_seq;
INSERT INTO credit_seq(next_val) VALUES (NVL((SELECT MAX(idCredit)+1 FROM credit),1));

DELETE FROM system_setting_seq;
INSERT INTO system_setting_seq(next_val) VALUES (NVL((SELECT MAX(idSystemSetting)+1 FROM systemsetting),1));

DELETE FROM reward_seq;
INSERT INTO reward_seq(next_val) VALUES (NVL((SELECT MAX(idReward)+1 FROM reward),1));

DELETE FROM lottery_raffle_seq;
INSERT INTO lottery_raffle_seq(next_val) VALUES (NVL((SELECT MAX(idLotteryRaffle)+1 FROM lotteryraffle),1));

-- Increment Refresh Token
DELETE FROM refresh_token_seq;
INSERT INTO refresh_token_seq(next_val) VALUES (NVL((SELECT MAX(idToken)+1 FROM refreshtoken),1));

-- Increment Audit
DELETE FROM audit_seq;
INSERT INTO audit_seq(next_val) VALUES (NVL((SELECT MAX(idAudit)+1 FROM audit),1));

-- Increment Product
DELETE FROM product_seq;
INSERT INTO product_seq(next_val) VALUES (NVL((SELECT MAX(idProduct)+1 FROM product),1));

-- Increment Price
DELETE FROM price_seq;
INSERT INTO price_seq(next_val) VALUES (NVL((SELECT MAX(idPrice)+1 FROM price),1));

-- Increment User Contribution
DELETE FROM user_contrib_seq;
INSERT INTO user_contrib_seq(next_val) VALUES (NVL((SELECT MAX(idUserContribution)+1 FROM usercontribution),1));

-- User Profile 
DELETE FROM user_profile_seq;
INSERT INTO user_profile_seq(next_val) VALUES (NVL((SELECT MAX(idProfile)+1 FROM profile),1));

-- User Profile 
DELETE FROM country_seq;
INSERT INTO country_seq(next_val) VALUES (NVL((SELECT MAX(idCountry)+1 FROM country),1));

commit;
