import {type ListingStruct} from "./listinginterface.ts";

export interface PurchasedListingQuantityDto {
  productListingId: number;
  quantity: number;
}

export interface OrderItem {
  listing: ListingStruct;
  quantity: number;
}