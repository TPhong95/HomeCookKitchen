import {type ListingStruct} from "./listinginterface.ts";

export interface PurchasedListingQuantityDto {
  productListingId: number;
  quantity: number;
}

export interface OrderItem {
  listing: ListingStruct;
  quantity: number;
}

// FIX: Skeleton type, still used by orders.tsx. Remove once orders.tsx uses OrderItem
export interface ActiveOrder {
  listingId: number;
  chefId: number;
  chefName: string;
  chefStreet: string;
  chefPostalCode: string;
  chefCity: string;
  recipeName: string;
  image: string;
  totalPrice: number;
  numberOfGuests: number;
  readyBy: string;
  purchaseDeadline: string;
}