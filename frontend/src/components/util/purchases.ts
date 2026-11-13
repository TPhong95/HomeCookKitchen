import {type ListingStruct} from "../shared/listinginterface.ts";
import {type OrderItem, type PurchasedListingQuantityDto} from "../shared/purchaseinterface.ts";

export async function loadCustomerOrders(customerId: number): Promise<OrderItem[]> {
  const res = await fetch(`http://localhost:8100/api/purchase/customer/${customerId}`);
  if (!res.ok) throw new Error(`Purchase request failed: ${res.status}`);
  const purchased: PurchasedListingQuantityDto[] = await res.json();

  const results = await Promise.allSettled(purchased.map(async (p): Promise<OrderItem> => {
    const listingRes = await fetch(`http://localhost:8100/api/productListing/${p.productListingId}`);
    if (!listingRes.ok) throw new Error(`Listing ${p.productListingId} failed: ${listingRes.status}`);
    const listing: ListingStruct = await listingRes.json();
    return {listing, quantity: p.quantity};
  }));

  results
    .filter(r => r.status === "rejected")
    .forEach(r => console.log(r.reason));

  return results
    .filter((r): r is PromiseFulfilledResult<OrderItem> => r.status === "fulfilled")
    .map(r => r.value);
}

export function readyByTime(item: OrderItem): number {
  return new Date(item.listing.readyBy).getTime();
}