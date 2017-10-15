<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">
    <xsl:template match="/">
        <html>
          <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-alpha.6/css/bootstrap.min.css" integrity="sha384-rwoIResjU2yc3z8GV/NPeZWAv56rSmLldC3R/AZzGRnGxQQKnKkoFVhFQhNUwEyJ" crossorigin="anonymous"/>
            <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-alpha.6/js/bootstrap.min.js" integrity="sha384-vBWWzlZJ8ea9aCX4pEW3rVHjgjt7zpkNpZk+02D9phzyeVkE+jo0ieGizqPLForn" crossorigin="anonymous"></script>
            <body>
                <h1>HTML Summary</h1>
                <table border="1" class="table table-striped table-hover">
                    <thead class="thead-inverse">
                        <tr>
                            <th>Image</th>
                            <th>Brand</th>
                            <th>Model</th>
                            <th>Series</th>
                            <th>Version</th>
                            <th>Segment</th>
                            <th>Fuel</th>
                            <th>Color</th>
                            <th>Year</th>
                            <th>Month</th>
                            <th>Metallic</th>
                            <th>Price</th>
                            <th>Fixed value?</th>
                            <th>Deductible VAT</th>
                            <th>Plate Number</th>
                            <th>Doors</th>
                            <th>Seats</th>
                            <th>Airbags</th>
                            <th>Alloy Wheels</th>
                            <th>Alloy Wheels Size</th>
                            <th>HP</th>
                            <th>Traction</th>
                            <th>Transmission</th>
                            <th>Gears</th>
                            <th>Displacement</th>
                            <th>Consumption</th>
                            <th>CO2 Emissions</th>
                            <th>Particles Filter</th>
                            <th>IUC</th>
                            <th>Inspection</th>
                            <th>Number of Registries</th>
                            <th>Origin</th>
                            <th>Condition</th>
                            <th>Review Book</th>
                            <th>Second Key</th>
                            <th>Milleage</th>
                            <th>Autonomy</th>
                            <th>Class</th>
                            <th>Upholstery</th>
                            <th>AC</th>
                            <th>Rooftop</th>
                            <th>Eletric Rooftop</th>
                            <th>Non Smoker</th>
                            <th>Cabrio</th>
                            <th>Warranty</th>
                            <th>VIN</th>
                            <th>Mecanical Warranty</th>
                            <th>Advertiser</th>
                            <th>Takeback</th>
                            <th>Financing</th>
                      </tr>
                    </thead>
                    <xsl:for-each select="advertisements/advert">
                        <xsl:sort select="price" order="ascending" data-type="number"/>
                        <tr>
                            <td>
                                <a target="_blank">
                                    <xsl:attribute name="href">
                                        <xsl:value-of select="url"/>
                                    </xsl:attribute>
                                    <img>
                                        <xsl:attribute name="src">
                                            <xsl:value-of select="imageUrl"/>
                                        </xsl:attribute>
                                    </img>
                                </a>
                            </td>
                            <td>
                                <xsl:value-of select="brand" />
                            </td>
                            <td>
                                <xsl:value-of select="model" />
                            </td>
                            <td>
                                <xsl:value-of select="series" />
                            </td>
                            <td>
                                <xsl:value-of select="version" />
                            </td>
                            <td>
                                <xsl:value-of select="segment" />
                            </td>
                            <td>
                                <xsl:value-of select="fuel" />
                            </td>
                            <td>
                                <xsl:value-of select="color" />
                            </td>
                            <td>
                                <xsl:value-of select="metallic" />
                            </td>
                            <td>
                                <xsl:value-of select="year" />
                            </td>
                            <td>
                                <xsl:value-of select="month" />
                            </td>
                            <td>
                                <xsl:value-of select="price" />
                                <xsl:value-of select="price/@units" />

                            </td>

                            <td>
                                <xsl:value-of select="fixedValue" />
                            </td>
                            <td>
                                <xsl:value-of select="deductibleVAT" />
                            </td>
                            <td>
                                <xsl:value-of select="plateNumber" />
                            </td>
                            <td>
                                <xsl:value-of select="numberOfDoors" />
                            </td>
                            <td>
                                <xsl:value-of select="numberOfSeats" />
                            </td>
                            <td>
                                <xsl:value-of select="numberOfAirbags" />
                            </td>
                            <td>
                                <xsl:value-of select="alloyWheels" />
                            </td>
                            <td>
                                <xsl:value-of select="alloyWheelsSize" />
                            </td>
                            <td>
                                <xsl:value-of select="horsePower" />
                                <xsl:value-of select="horsePower/@units" />
                            </td>
                            <td>
                                <xsl:value-of select="traction" />
                            </td>
                            <td>
                                <xsl:value-of select="transmission" />
                            </td>
                            <td>
                                <xsl:value-of select="numberOfGears" />
                            </td>
                            <td>
                                <xsl:value-of select="displacement" />
                                <xsl:value-of select="displacement/@units" />
                            </td>
                            <td>
                                <xsl:value-of select="consumption" />
                                <xsl:value-of select="consumption/@units" />
                            </td>
                            <td>
                                <xsl:value-of select="CO2Emissions" />
                                <xsl:value-of select="CO2Emissions/@units" />
                            </td>
                            <td>
                                <xsl:value-of select="particlesFilter" />
                            </td>
                            <td>
                                <xsl:value-of select="iuc" />
                                <xsl:value-of select="iuc/@units" />
                            </td>
                            <td>
                                <xsl:value-of select="inspectionValidUntil" />
                            </td>
                            <td>
                                <xsl:value-of select="numberOfRegisters" />
                            </td>
                            <td>
                                <xsl:value-of select="origin" />
                            </td>
                            <td>
                                <xsl:value-of select="condition" />
                            </td>
                            <td>
                                <xsl:value-of select="reviewBook" />
                            </td>
                            <td>
                                <xsl:value-of select="secondKey" />
                            </td>
                            <td>
                                <xsl:value-of select="mileage" />
                                <xsl:value-of select="mileage/@units" />
                            </td>
                            <td>
                                <xsl:value-of select="autonomy" />
                            </td>
                            <td>
                                <xsl:value-of select="vehicleClass" />
                            </td>
                            <td>
                                <xsl:value-of select="upholstery" />
                            </td>
                            <td>
                                <xsl:value-of select="airConditioning" />
                            </td>
                            <td>
                                <xsl:value-of select="roofTop" />
                            </td>
                            <td>
                                <xsl:value-of select="electricRoofTop" />
                            </td>
                            <td>
                                <xsl:value-of select="nonSmoker" />
                            </td>
                            <td>
                                <xsl:value-of select="cabrio" />
                            </td>
                            <td>
                                <xsl:value-of select="warranty" />
                            </td>
                            <td>
                                <xsl:value-of select="vin" />
                            </td>
                            <td>
                                <xsl:value-of select="mechanicalWarranty" />
                            </td>
                            <td>
                                <xsl:value-of select="advertiser" />
                            </td>
                            <td>
                                <xsl:value-of select="takeBack" />
                            </td>
                            <td>
                                <xsl:value-of select="financing" />
                            </td>
                            <xsl:for-each select="extras/extra">
                                    <xsl:value-of select="extra"/>
                            </xsl:for-each>

                        </tr>
                    </xsl:for-each>
                </table>
            </body>
        </html>
    </xsl:template>
</xsl:stylesheet>
