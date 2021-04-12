// Define some defaults for our queries.
const endPoint = 'https://api.qrserver.com/v1/create-qr-code/'
const defaultSize = '150'

// Convert our string to a QR Code.
export async function getQRCode(inputString, size) {
    // Create our request.
    const searchParams = new URLSearchParams({
        'data': inputString,
        'size': (size) ? size : defaultSize
    }).toString()
    
    const url = new URL(`${endPoint}?${searchParams}`)

    // Try to fetch our QR Code.  If we get it return an obj that contains the orig inputString and the image.
    const response = await fetch(url)
    if (!response.ok)
        throw new Error(`HTTP Error: ${response.status}`)
    
    return ({
        input: inputString,
        image: URL.createObjectURL(await response.blob()),
    })
}